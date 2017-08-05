package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.mapper.IRecruitmentMapper;
import com.webapps.service.IRecruitmentService;

@Service
@Transactional
public class RecruitmentServiceImpl implements IRecruitmentService {
	
	@Autowired
	private IRecruitmentMapper iRecruitmentMapper;

	@Override
	public int saveRecruitment(RecruitmentRequestForm form) throws Exception {
		Company company = new Company();
		company.setId(form.getCompanyId());
		form.setCompany(company);
		form.setEndDate(DateUtil.parseDateByStr(form.getEndDateStr(), DateUtil.FULL_PATTERN));
		form.setPublishTime(DateUtil.parseDateByStr(form.getPublishTimeStr(), DateUtil.FULL_PATTERN));
		int result = 0;
		if(form.getId()==null){
			result = iRecruitmentMapper.insert(form);
		}else{
			result = iRecruitmentMapper.updateById(form.getId(), form);
		}
		return result;
	}

	@Override
	public List<Recruitment> queryListByCompanyId(Integer companyId) throws Exception {
		List<Recruitment> list = iRecruitmentMapper.queryByCompanyId(companyId);
		return list;
	}

	@Override
	public int deleteRecruitmentById(Integer id) throws Exception {
		int result = iRecruitmentMapper.deleteByIdInLogic(id);
		return result;
	}
	
	

}
