package com.webapps.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.constant.ExcelHeaders;
import com.webapps.common.dto.EnrollmentExportDto;
import com.webapps.common.dto.RateDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.User;
import com.webapps.common.form.RateDtoRequestForm;
import com.webapps.common.utils.CommonUtil;
import com.webapps.common.utils.ExcelPoiUtil;
import com.webapps.common.utils.FileUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.mapper.IRateDtoMapper;
import com.webapps.service.IRateService;

@Service
@Transactional
public class RateServiceImpl implements IRateService {
	
	private static Logger logger = Logger.getLogger(RateServiceImpl.class);

	@Autowired
	private IRateDtoMapper iRateDtoMapper;

	@Autowired
	private IEnrollmentMapper iEnrollmentMapper;

	@Override
	public Page loadRateDtoList(Page page, RateDtoRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iRateDtoMapper.queryCount(form);
		List<RateDto> list = iRateDtoMapper.queryPage(startRow, rows, form);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public Page loadEntryStatisticsList(Page page, RateDtoRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iEnrollmentMapper.queryCountForEntryStatistics(form);
		List<Enrollment> list = iEnrollmentMapper.queryPageForEntryStatistics(startRow, rows, form);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public Page loadExpireStatisticsList(Page page, RateDtoRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iEnrollmentMapper.queryCountForExpireStatistics(form);
		List<Enrollment> list = iEnrollmentMapper.queryPageForExpireStatistics(startRow, rows, form);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public Page loadEntryOrExprieStatisticsList(Page page, RateDtoRequestForm form) throws Exception {
		return null;
	}
	
	public void exportStatistics(HttpSession session,HttpServletResponse response,
			int talkerId,int state,int type){
		RateDtoRequestForm form = new RateDtoRequestForm();
    	form.setState(state);
    	User user = (User)session.getAttribute("user");
    	form.setTalkerId(talkerId);
    	List<Enrollment> list = null;
    	String filePath = null;
    	String excelPath = null;
    	try {
			if(type==1){
				list = iEnrollmentMapper.queryEntryStatisticsForExport(form);
				excelPath = (String) PropertiesUtil.getProperty("entry_excel_path");
				
			}else if(type==2){
				list = iEnrollmentMapper.queryExpireStatisticsForExport(form);
				excelPath = (String) PropertiesUtil.getProperty("expire_excel_path");
			}
			if(CollectionUtils.isNotEmpty(list)){
				List<EnrollmentExportDto> dtoList = getEnrollmentExportDtoList(list);
				filePath = excelPath + File.separator + CommonUtil.getUUID() + ".xls";
				OutputStream out = null;
				try {
					out = new FileOutputStream(filePath);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				ExcelPoiUtil<EnrollmentExportDto> poi = new ExcelPoiUtil<>();
				poi.exportExcel("入职统计数据", ExcelHeaders.EXPORT_ENTRY_STATISTICS_HEADERS, ExcelHeaders.EXPORT_ENTRY_STATISTICS_FIELDS, dtoList, out);
				FileUtil.downloadFile(response, filePath);
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private List<EnrollmentExportDto> getEnrollmentExportDtoList(List<Enrollment> list){
		List<EnrollmentExportDto> resultList = new ArrayList<EnrollmentExportDto>();
		int index = 1;
		for(Enrollment em:list){
			EnrollmentExportDto dto = new EnrollmentExportDto();
			dto.setCashbackDays(em.getCashbackDays());
			dto.setCompany(em.getCompany());
			dto.setCreateTime(em.getCreateTime());
			dto.setDataState(em.getDataState());
			dto.setEntryApproverId(em.getEntryApproverId());
			dto.setEntryApproverName(em.getEntryApproverName());
			dto.setEntryDate(em.getEntryDate());
			dto.setExpireApproverId(em.getExpireApproverId());
			dto.setExpireApproverName(em.getExpireApproverName());
			dto.setFailedReason(em.getFailedReason());
			dto.setId(em.getId());
			dto.setIndex(index);
			dto.setIntentionCityId(em.getIntentionCityId());
			dto.setIntentionCityName(em.getIntentionCityName());
			dto.setInterviewIntention(em.getInterviewIntention());
			dto.setInterviewTime(em.getInterviewTime());
			dto.setIsEntryApproved(em.getIsEntryApproved());
			dto.setIsExpireApproved(em.getIsExpireApproved());
			dto.setIsHistory(em.getIsHistory());
			dto.setIsLatest(em.getIsLatest());
			dto.setIsTalked(em.getIsTalked());
			dto.setOldTalkerId(em.getOldTalkerId());
			dto.setOldTalkerName(em.getOldTalkerName());
			dto.setOperatorId(em.getOperatorId());
			dto.setRecruitment(em.getRecruitment());
			dto.setRemark(em.getRemark());
			dto.setReward(em.getReward());
			dto.setState(em.getState());
			dto.setTalkerId(em.getTalkerId());
			dto.setTalkResult(em.getTalkResult());
			dto.setUpdateTime(em.getUpdateTime());
			dto.setUser(em.getUser());
			index++;
			resultList.add(dto);
		}
		return resultList;
	}
}
