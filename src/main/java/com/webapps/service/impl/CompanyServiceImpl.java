package com.webapps.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.mapper.ICompanyMapper;
import com.webapps.mapper.IPictureMapper;
import com.webapps.service.ICompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService {
	
	private static Logger logger = Logger.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private ICompanyMapper iCompanyMapper;
	
	@Autowired
	private IPictureMapper iPictureMapper;

	@Override
	public Page loadCompanyList(Page page, CompanyRequestForm company) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iCompanyMapper.queryCount(company);
		List<Company> list = iCompanyMapper.queryPage(startRow, endRow, company);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public Company getById(Integer id) throws Exception {
		Company company = iCompanyMapper.getById(id);
		if(company!=null){
			List<Picture> pics = iPictureMapper.queryListByFkId(company.getId());
			company.setPictures(pics);
		}
		return company;
	}

	@Override
	public ResultDto<Company> saveCompany(Company company) throws Exception {
		ResultDto<Company> dto = new ResultDto<Company>();
		if(company.getId()!=null){
			int result = iCompanyMapper.updateById(company.getId(), company);
			if(result == 0){
				dto.setResult("update_fail");
				dto.setErrorMsg("更新公司信息失败");
				return dto;
			}
			ResultDto<Picture> dto1 = saveBannerPicture(company);
			if("S".equals(dto1.getResult())){
				dto.setResult("update_success");
				dto.setData(company);
			}else{
				dto.setResult("update_fail");
				dto.setErrorMsg("更新公司banner图片失败");
			}
			return dto;
		}else{
			try {
				int result = iCompanyMapper.insert(company);
				dto.setData(company);
				if(result==0){
					dto.setResult("insert_fail");
					dto.setErrorMsg("新增失败");
					return dto;
				}
				ResultDto<Picture> dto1 = saveBannerPicture(company);
				if("S".equals(dto1.getResult())){
					dto.setResult("insert_success");
					dto.setData(company);
				}else{
					dto.setResult("insert_fail");
					dto.setErrorMsg("保存公司banner图片失败");
				}
			} catch (DuplicateKeyException e) {
				logger.error(e.getMessage());
				dto.setData(company);
				dto.setResult("insert_fail");
				dto.setErrorMsg("该公司已被注册，请更换重试");
				return dto;
			}
		}
		return dto;
	}
	
	private ResultDto<Picture> saveBannerPicture(Company form){
		ResultDto<Picture> dto = new ResultDto<Picture>();
		try {
			//找到老的banner图片，置为删除状态
			List<Picture> list = iPictureMapper.queryListByFkIdAndType(form.getId(), 6);
			if(CollectionUtils.isNotEmpty(list)){
				iPictureMapper.batchDeleteInLogic(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setResult("F");
			dto.setErrorMsg("删除发布单老banner图片时异常");
			return dto;
		}
		//保存新的图片
		Picture p = new Picture();
		p.setDataState(1);
		p.setFkId(form.getId());
		p.setPicUrl(form.getPicture().getPicUrl());
		p.setType(6);
		p.setTitle(form.getName());
		p.setRemark("公司banner图片");
		int result = iPictureMapper.insert(p);
		if(result == 1 ){
			dto.setData(p);
			dto.setResult("S");
		}else{
			dto.setErrorMsg("保存发布单banner图片失败");
			dto.setResult("F");
		}
		return dto;
	}

	@Override
	public ResultDto<Company> deleteCompanyById(Integer id) throws Exception {
		ResultDto<Company> dto = new ResultDto<Company>();
		iCompanyMapper.deleteByIdInLogic(id);
		dto.setResult("S");
		return dto;
	}

}
