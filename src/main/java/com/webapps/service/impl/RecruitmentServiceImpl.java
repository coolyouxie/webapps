package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.Recruitment;
import com.webapps.common.form.RecruitmentRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.mapper.ICompanyMapper;
import com.webapps.mapper.IRecruitmentMapper;
import com.webapps.service.IRecruitmentService;

@Service
@Transactional
public class RecruitmentServiceImpl implements IRecruitmentService {
	
	@Autowired
	private IRecruitmentMapper iRecruitmentMapper;
	
	@Autowired
	private ICompanyMapper iCompanyMapper;

	@Override
	public ResultDto<RecruitmentRequestForm> saveRecruitment(RecruitmentRequestForm form) {
		ResultDto<RecruitmentRequestForm> dto = new ResultDto<RecruitmentRequestForm>();
		Company company = new Company();
		company.setId(form.getCompanyId());
		form.setCompany(company);
		form.setEndDate(DateUtil.parseDateByStr(form.getEndDateStr(), DateUtil.FULL_PATTERN));
		form.setPublishTime(DateUtil.parseDateByStr(form.getPublishTimeStr(), DateUtil.FULL_PATTERN));
		int result = 0;
		String errorMsg = null;
		if(form.getId()==null){
			result = iRecruitmentMapper.insert(form);
			errorMsg = "新增失败";
		}else{
			try {
				result = iRecruitmentMapper.updateById(form.getId(), form);
			} catch (Exception e) {
				e.printStackTrace();
			}
			errorMsg = "更新失败";
		}
		if(result != 1){
			dto.setErrorMsg(errorMsg);
			dto.setResult("F");
		}else{
			dto.setResult("S");
		}
		dto.setData(form);
		return dto;
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

	@Override
	public Page loadRecruitmentList(Page page, RecruitmentRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iRecruitmentMapper.queryCount(form);
		List<Recruitment> list = iRecruitmentMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public Recruitment getById(Integer id) throws Exception {
		Recruitment r = iRecruitmentMapper.getById(id);
		if(r!=null&&r.getCompany()!=null&&r.getCompany().getId()!=null){
			Company c = iCompanyMapper.getById(r.getCompany().getId());
			r.setCompany(c);
		}
		return r;
	}
	
	

}
