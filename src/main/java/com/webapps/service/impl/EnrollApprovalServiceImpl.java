package com.webapps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.common.utils.DateUtil;
import com.webapps.mapper.IEnrollApprovalMapper;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.service.IEnrollApprovalService;

import net.sf.json.JSONObject;

@Service
@Transactional
public class EnrollApprovalServiceImpl implements IEnrollApprovalService {
	
	@Autowired
	private IEnrollApprovalMapper iEnrollApprovalMapper;
	
	@Autowired
	private IEnrollmentMapper iEnrollmentMapper;

	@Override
	public Page loadEnrollApprovalList(Page page, EnrollApprovalRequestForm form) throws Exception {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iEnrollApprovalMapper.queryCount(form);
		List<EnrollApproval> result = iEnrollApprovalMapper.queryPage(startRow, endRow, form);
		page.setResultList(result);
		page.setRecords(count);
		return page;
	}

	/**
	 * approvalType：1入职审核，2期满审核
	 */
	@Override
	public ResultDto<EnrollApproval> approvalById(Integer id,Integer state,String remark) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		ea.setState(state);
		ea.setRemark(remark);
		int result = iEnrollApprovalMapper.updateById(id, ea);
		dto.setData(ea);
		if(result == 1){
			dto.setResult("S");
		}else{
			dto.setResult("F");
			dto.setErrorMsg("审核信息更新失败，请稍后再试");
		}
		return dto;
	}

	@Override
	public EnrollApproval getById(Integer id) throws Exception {
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		return ea;
	}

	@Override
	public ResultDto<EnrollApproval> enrollApproval(Integer id, Integer state, 
			String failedReason,BigDecimal reward) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		if(ea==null){
			dto.setErrorMsg("未找到待审核信息，请刷新页面后再试");
			dto.setResult("F");
			return dto;
		}
		Enrollment enrollment = iEnrollmentMapper.getById(ea.getEnrollmentId());
		if(enrollment==null){
			dto.setErrorMsg("未找到报名信息，请确认后再次审核");
			dto.setResult("F");
			return dto;
		}
		if(enrollment.getState()==20&&ea.getType()==1){
			//入职审核
			if(state==1){
				enrollment.setState(21);
				enrollment.setUpdateTime(new Date());
				ea.setReward(reward);
				ea.setState(1);
				ea.setUpdateTime(new Date());
				ea.setReward(reward);
			}else{
				enrollment.setState(22);
				enrollment.setUpdateTime(new Date());
				ea.setState(2);
				ea.setUpdateTime(new Date());
				iEnrollmentMapper.updateById(enrollment.getId(), enrollment);
				iEnrollApprovalMapper.updateById(ea.getId(), ea);
			}
			dto.setResult("S");
			return dto;
		}else{
			dto.setErrorMsg("审核状态不匹配，请刷新页面后再试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<EnrollApproval> expireApproval(Integer id, Integer state, String failedReason) throws Exception {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		if(ea==null){
			dto.setErrorMsg("未找到待审核信息，请刷新页面后再试");
			dto.setResult("F");
			return dto;
		}
		Enrollment enrollment = iEnrollmentMapper.getById(ea.getEnrollmentId());
		if(enrollment==null){
			dto.setErrorMsg("未找到报名信息，请确认后再次审核");
			dto.setResult("F");
			return dto;
		}
		if(enrollment.getState()==30&&ea.getType()==2){
			//入职审核
			if(state==1){
				enrollment.setState(21);
				enrollment.setUpdateTime(new Date());
				ea.setState(1);
				ea.setUpdateTime(new Date());
			}else{
				enrollment.setState(22);
				enrollment.setUpdateTime(new Date());
				enrollment.setFailedReason(failedReason);
				ea.setState(2);
				ea.setUpdateTime(new Date());
				ea.setFailedReason(failedReason);
				iEnrollmentMapper.updateById(enrollment.getId(), enrollment);
				iEnrollApprovalMapper.updateById(ea.getId(), ea);
			}
			dto.setResult("S");
			return dto;
		}else{
			dto.setErrorMsg("审核状态不匹配，请刷新页面后再试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public ResultDto<EnrollApproval> applyEntryApproval(JSONObject params) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		Integer enrollemntId = params.getInt("enrollemntId");
		String entryDateStr = params.getString("entryDate");
		if(StringUtils.isBlank(entryDateStr)){
			dto.setErrorMsg("入职日期未填写");
			dto.setResult("F");
			return dto;
		}
		Date entryDate = DateUtil.parseDateByStr(entryDateStr, "yyyy-MM-dd");
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(enrollemntId);
			if(enrollment!=null){
				if(enrollment.getState()!=1&&enrollment.getState()!=22){
					dto.setErrorMsg("报名信息不是入职待审核状态，不可审核");
					dto.setResult("F");
					return dto;
				}
				enrollment.setState(20);
				enrollment.setUpdateTime(new Date());
				enrollment.setEntryDate(entryDate);
				iEnrollmentMapper.updateById(enrollemntId, enrollment);
				EnrollApproval ea = saveEnrollApprova(entryDate, enrollment,1);
				dto.setResult("S");
				dto.setData(ea);
				return dto;
			}else{
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("发起入职审核申请失败，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	private EnrollApproval saveEnrollApprova(Date entryDate, Enrollment enrollment,Integer type) {
		EnrollApproval ea = new EnrollApproval();
		ea.setEnrollmentId(enrollment.getId());
		ea.setDataState(1);
		ea.setEntryDate(entryDate);
		ea.setState(0);
		ea.setType(type);
		iEnrollApprovalMapper.insert(ea);
		return ea;
	}

	@Override
	public ResultDto<EnrollApproval> applyExpireApproval(JSONObject params) {
		ResultDto<EnrollApproval> dto = new ResultDto<EnrollApproval>();
		Integer enrollmentId = params.getInt("enrollmentId");
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(enrollmentId);
			if(enrollment==null){
				dto.setErrorMsg("报名信息无效，请稍后重试");
				dto.setResult("F");
				return dto;
			}
			if(enrollment.getState()!=21&&enrollment.getState()==32){
				dto.setErrorMsg("报名信息不是已入职状态，不可审核");
				dto.setResult("F");
				return dto;
			}
			enrollment.setState(20);
			enrollment.setUpdateTime(new Date());
			iEnrollmentMapper.updateById(enrollmentId, enrollment);
			EnrollApproval ea = saveEnrollApprova(null, enrollment,2);
			dto.setResult("S");
			dto.setData(ea);
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("发起期满审核申请失败，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

}
