package com.webapps.service.impl;

import java.util.List;

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
			iCompanyMapper.updateById(company.getId(), company);
			dto.setResult("update_success");
			dto.setData(company);
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
				dto.setResult("insert_success");
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

	@Override
	public ResultDto<Company> deleteCompanyById(Integer id) throws Exception {
		ResultDto<Company> dto = new ResultDto<Company>();
		iCompanyMapper.deleteByIdInLogic(id);
		dto.setResult("success");
		return dto;
	}

}
