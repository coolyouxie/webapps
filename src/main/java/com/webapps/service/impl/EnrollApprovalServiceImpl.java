package com.webapps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.EnrollApproval;
import com.webapps.common.form.EnrollApprovalRequestForm;
import com.webapps.mapper.IEnrollApprovalMapper;
import com.webapps.service.IEnrollApprovalService;

@Service
@Transactional
public class EnrollApprovalServiceImpl implements IEnrollApprovalService {
	
	@Autowired
	private IEnrollApprovalMapper iEnrollApprovalMapper;

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
			dto.setResult("success");
		}else{
			dto.setResult("fail");
			dto.setErrorMsg("审核信息更新失败，请稍后再试");
		}
		return dto;
	}

	@Override
	public EnrollApproval getById(Integer id) throws Exception {
		EnrollApproval ea = iEnrollApprovalMapper.getById(id);
		return ea;
	}

}
