package com.webapps.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.dto.UserApproveCountDto;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.mapper.IUserApproveCountMapper;
import com.webapps.mapper.IUserMapper;
import com.webapps.service.IEnrollmentService;

@Service
@Transactional
public class EnrollmentServiceImpl implements IEnrollmentService {
	
	private static Logger logger = Logger.getLogger(EnrollmentServiceImpl.class);
	
	@Autowired
	private IEnrollmentMapper iEnrollmentMapper;
	
	@Autowired
	private IUserMapper iUserMapper;
	
	@Autowired
	private IUserApproveCountMapper iUserApproveCountMapper;

	@Override
	public Page loadEnrollmentList(Page page, EnrollmentRequestForm enrollment) throws Exception {
		int startRow = page.getStartRow();
		int rows = page.getRows();
		int count = iEnrollmentMapper.queryCount(enrollment);
		List<Enrollment> list = iEnrollmentMapper.queryPage(startRow, rows, enrollment);
		page.setResultList(list);
		page.setRecords(count);
		page.countRecords(count);
		return page;
	}

	@Override
	public Enrollment getById(Integer id) throws Exception {
		Enrollment em = iEnrollmentMapper.getById(id);
		return em;
	}

	/**
	 * 报名，在APP端也要限制，如果发现某用户已经报了某个发布单，则报名按钮置灰，显示“已报名”
	 */
	@Override
	public ResultDto<Enrollment> saveEnrollment(Enrollment enrollment) throws Exception {
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		int count = iEnrollmentMapper.countByFkIds(enrollment);
		if(count>=1){
			dto.setResult("F");
			dto.setErrorMsg("不能重复报名");
			return dto;
		}
		int result = iEnrollmentMapper.insert(enrollment);
		if(result==1){
			dto.setResult("S");
			return dto;
		}else{
			dto.setResult("F");
			dto.setErrorMsg("报名失败，请稍后再试");
			return dto;
		}
	}

	@Override
	public ResultDto<Enrollment> deleteEnrollmentById(Integer id) throws Exception {
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		int result = iEnrollmentMapper.deleteByIdInLogic(id);
		if(result==1){
			dto.setResult("S");
			return dto;
		}else{
			dto.setResult("F");
			dto.setErrorMsg("取消失败");
			return dto;
		}
	}

	@Override
	public List<Enrollment> queryEnrollmentListByUserId(Integer id) throws Exception {
		EnrollmentRequestForm form = new EnrollmentRequestForm();
		User user = new User();
		user.setId(id);
		form.setUser(user);
		List<Enrollment> list = iEnrollmentMapper.queryListByFkId(form);
		return list;
	}

	@Override
	public int saveTalkInfoById(Enrollment em) throws Exception {
		int count = iEnrollmentMapper.saveTalkInfoById(em,em.getId());
		return count;
	}

	@Override
	public ResultDto<Enrollment> userEnroll(Enrollment em) throws Exception {
		ResultDto<Enrollment> dto = null;
		em.setIsTalked(0);
		em.setState(1);
		em.setDataState(1);
		int count = iEnrollmentMapper.countByFkIds(em);
		if(count>=1){
			dto = new ResultDto<Enrollment>();
			dto.setErrorMsg("已经报名了本次招工，请不要重复申请");
			dto.setResult("F");
			return dto;
		}
		EnrollmentRequestForm form = new EnrollmentRequestForm();
		form.setUser(em.getUser());
		List<Enrollment> list = iEnrollmentMapper.queryListByFkId(form);
		User user = null;
		user = iUserMapper.getById(em.getUser().getId());
		if(CollectionUtils.isNotEmpty(list)){
			Enrollment em1 = list.get(0);
			if(em1.getState()==4||user.getCurrentState()==0){
				user.setUpdateTime(new Date());
				user.setCurrentState(1);
			}
		}
		int result = iEnrollmentMapper.insert(em);
		//随机分配客服专员
		setApproverInfo(em);
		iUserMapper.updateById(user.getId(),user);
		if(result == 1){
			dto = new ResultDto<Enrollment>();
			dto.setData(em);
			dto.setResult("S");
		}else{
			dto = new ResultDto<Enrollment>();
			dto.setErrorMsg("保存报名信息异常");
			dto.setResult("F");
		}
		return dto;
	}
	
	private void setApproverInfo(Enrollment em){
		List<UserApproveCountDto> list = iUserApproveCountMapper.queryTalkCount();
		if(CollectionUtils.isNotEmpty(list)){
			int len = list.size();
			double dblR = Math.random() * len;
			int intR = (int) Math.floor(dblR);
			em.setTalkerId(list.get(intR).getUserId());
			em.setTalkerName(list.get(intR).getName());
			em.setUpdateTime(new Date());
			try {
				iEnrollmentMapper.updateById(em.getId(), em);
			} catch (Exception e) {
				logger.error("设置客服专员时异常");
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResultDto<Enrollment> updateEnrollment(EnrollmentRequestForm form) {
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		int result;
		try {
			result = iEnrollmentMapper.updateById(form.getId(), form);
			if(result!=1){
				dto.setErrorMsg("报名信息更新失败，请稍后重试");
				dto.setResult("F");
			}else{
				dto.setResult("S");
			}
		} catch (Exception e) {
			dto.setErrorMsg("报名信息更新异常，请稍后重试");
			dto.setResult("F");
			e.printStackTrace();
		}
		dto.setData(form);
		return dto;
	}
	
	@Override
	public ResultDto<Enrollment> cancelEnroll(EnrollmentRequestForm form){
		ResultDto<Enrollment> dto = new ResultDto<Enrollment>();
		try {
			Enrollment enrollment = iEnrollmentMapper.getById(form.getId());
			if(enrollment.getState()!=1){
				dto.setErrorMsg("报名信息状态不符合可取消条件");
				dto.setResult("F");
				return dto;
			}
			enrollment.setDataState(0);
			enrollment.setUpdateTime(new Date());
			int result = iEnrollmentMapper.cancelEnroll(enrollment,enrollment.getId());
			if(result!=1){
				dto.setErrorMsg("报名信息更新失败，请稍后重试");
				dto.setResult("F");
			}else{
				dto.setResult("S");
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			dto.setErrorMsg("报名信息更新异常，请稍后重试");
			dto.setResult("F");
			return dto;
		}
	}

	@Override
	public Enrollment getCurrentStateEnrollmentByUserId(Integer userId) {

		return null;
	}

}
