package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Agency;
import com.webapps.common.form.AgencyRequestForm;
import com.webapps.mapper.IAgencyMapper;
import com.webapps.service.IAgencyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgencyServiceImpl implements IAgencyService {
	
	private static Logger logger = Logger.getLogger(AgencyServiceImpl.class);

	@Autowired
	private IAgencyMapper iAgencyMapper;

	public Page loadAgencyList(Page page, AgencyRequestForm agency) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iAgencyMapper.queryCount(agency);
		List<Agency> list = iAgencyMapper.queryPage(startRow, rows, agency);
		page.setResultList(list);
		page.countRecords(count);
		return page;
	}

	@Override
	public Agency getById(Integer id) throws Exception {
		Agency agency = iAgencyMapper.getById(id);
		return agency;
	}

	@Override
	public ResultDto<String> saveAgency(Agency agency) throws Exception {
		ResultDto<String> dto = new ResultDto<>();
		if(agency.getId()!=null){
			int result = iAgencyMapper.updateById(agency.getId(), agency);
			if(result == 0){
				dto.setResult("F");
				dto.setErrorMsg("更新门信息失败");
				return dto;
			}
			dto.setResult("S");
			return dto;
		}else{
			try {
				int result = iAgencyMapper.insert(agency);
				if(result==0){
					dto.setResult("F");
					dto.setErrorMsg("新增门店信息失败");
					return dto;
				}
				dto.setResult("S");
				return dto;
			} catch (DuplicateKeyException e) {
				logger.error(e.getMessage());
				dto.setResult("F");
				dto.setErrorMsg("保存门店信息异常");
				return dto;
			}
		}
	}


	@Override
	public ResultDto<String> deleteAgencyById(Integer id) throws Exception {
		ResultDto<String> dto = new ResultDto<String>();
		iAgencyMapper.deleteByIdInLogic(id);
		dto.setResult("S");
		return dto;
	}

}
