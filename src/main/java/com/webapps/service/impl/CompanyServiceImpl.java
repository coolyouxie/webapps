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
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Picture;
import com.webapps.common.form.CompanyRequestForm;
import com.webapps.mapper.IBannerConfigMapper;
import com.webapps.mapper.ICompanyMapper;
import com.webapps.mapper.IPictureMapper;
import com.webapps.service.ICompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService {
	
	private static Logger logger = Logger.getLogger(CompanyServiceImpl.class);
	
	private static Integer[] picTypes = new Integer[]{1,2,3};
	
	@Autowired
	private ICompanyMapper iCompanyMapper;
	
	@Autowired
	private IPictureMapper iPictureMapper;
	
	@Autowired
	private IBannerConfigMapper iBannerConfigMapper;

	@Override
	public Page loadCompanyList(Page page, CompanyRequestForm company) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iCompanyMapper.queryCount(company);
		List<Company> list = iCompanyMapper.queryPage(startRow, rows, company);
		page.setResultList(list);
		page.countRecords(count);
		return page;
	}

	@Override
	public Company getById(Integer id) throws Exception {
		Company company = iCompanyMapper.getById(id);
		if(company!=null){
			List<Picture> pics = iPictureMapper.queryByFkIdTypes(company.getId(),picTypes);
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
			ResultDto<BannerConfig> dto1 = saveBannerPicture(company);
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
				ResultDto<BannerConfig> dto1 = saveBannerPicture(company);
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
	
	private ResultDto<BannerConfig> saveBannerPicture(Company form){
		ResultDto<BannerConfig> dto = new ResultDto<BannerConfig>();
		dto.setResult("S");
		try {
			List<BannerConfig> list = iBannerConfigMapper.getByFkIdAndType(form.getId(), 3);
			if(CollectionUtils.isNotEmpty(list)){
				if(form.getIsBanner()==2){
					//如果不是banner，则删除之前设置的banner信息
					int count = iBannerConfigMapper.batchDeleteInLogic(list);
					if(count==list.size()){
						dto.setResult("S");
					}else{
						dto.setResult("F");
						dto.setErrorMsg("删除banner数据时出错");
					}
					return dto;
				}
				BannerConfig bc = list.get(0);
				bc.setPicUrl(form.getBannerConfig().getPicUrl());
				int result = iBannerConfigMapper.updateById(bc.getId(), bc);
				if(result==1){
					dto.setResult("S");
					dto.setData(bc);
				}else{
					dto.setErrorMsg("更新发布单banner信息出错");
					dto.setResult("F");
				}
				return dto;
			}
			if(form.getIsBanner()==1){
				BannerConfig bc = new BannerConfig();
				bc.setUpdateTime(null);
				bc.setDataState(1);
				bc.setPicUrl(form.getBannerConfig().getPicUrl());
				bc.setFkId(form.getId());
				bc.setTitle(form.getName());
				bc.setType(3);
				int result = iBannerConfigMapper.insert(bc);
				if(result==1){
					dto.setResult("S");
					dto.setData(bc);
				}else{
					dto.setErrorMsg("保存发布单banner信息出错");
					dto.setResult("F");
				}
			}
			return dto;
		} catch (Exception e) {
			logger.error("保存或更新发布单banner信息异常："+e.getMessage());
			dto.setResult("F");
			dto.setErrorMsg("保存或更新发布单banner信息异常");
			return dto;
		}
	}

	@Override
	public ResultDto<Company> deleteCompanyById(Integer id) throws Exception {
		ResultDto<Company> dto = new ResultDto<Company>();
		iCompanyMapper.deleteByIdInLogic(id);
		dto.setResult("S");
		return dto;
	}

}
