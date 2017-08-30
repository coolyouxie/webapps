package com.webapps.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.MessageConfig;
import com.webapps.common.entity.Entity;
import com.webapps.common.entity.OperateLog;
import com.webapps.common.form.MessageConfigRequestForm;
import com.webapps.mapper.IMessageConfigMapper;
import com.webapps.mapper.IOperateLogMapper;
import com.webapps.service.IMessageConfigService;

@Service
@Transactional
public class MessageConfigServiceImpl implements IMessageConfigService {
	
	private static Logger logger = Logger.getLogger(MessageConfigServiceImpl.class);
	
	@Autowired
	private IMessageConfigMapper iMessageConfigMapper;
	
	@Autowired
	private IOperateLogMapper iOperateLogMapper;

	@Override
	public ResultDto<MessageConfig> saveMessageConfig(MessageConfigRequestForm form) {
		int result = 0;
		String errorMsg = "";
		ResultDto<MessageConfig> dto = new ResultDto<MessageConfig>();
		int type = 0;
		String remark = null;
		if(form.getId()!=null){
			try {
				result = iMessageConfigMapper.updateById(form.getId(), form);
				errorMsg = "更新失败，请稍后再试";
				type = 2;
				remark = "更新Banner数据";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result = iMessageConfigMapper.insert(form);
			errorMsg = "新增失败，请稍后再试";
			type = 1;
			remark = "新增Banner数据";
		}
		if(result!=1){
			dto.setResult("F");
			logger.error(errorMsg);
			dto.setErrorMsg(errorMsg);
		}else{
			dto.setResult("S");
			dto.setData(form);
		}
		saveOperateLog(form, errorMsg, dto.getResult(), type, remark);
		return dto;
	}
	
	private void saveOperateLog(Entity entity,String errorMsg,String result,Integer type,String remark){
		OperateLog obj = new OperateLog();
		obj.setOperatorId(entity.getOperatorId());
		obj.setFkId(entity.getId());
		obj.setResult(result);
		obj.setErrorMsg(errorMsg);
		obj.setCreateTime(entity.getUpdateTime());
		iOperateLogMapper.insert(obj);
	}

	@Override
	public int deleteMessageConfigById(MessageConfigRequestForm form) {
		int count = 0;
		String errorMsg = null;
		String remark = "删除Banner数据";
		String result = null;
		try {
			count = iMessageConfigMapper.deleteByIdInLogic(form.getId());
			if(count==1){
				result = "S";
			}else{
				result = "F";
				errorMsg = "删除失败";
			}
		} catch (Exception e) {
			result = "F";
			errorMsg = "逻辑删除Banner数据异常";
			e.printStackTrace();
		}
		saveOperateLog(form, errorMsg, result, 3, remark);
		return count;
	}

	@Override
	public Page loadMessageConfigList(Page page, MessageConfigRequestForm form) {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iMessageConfigMapper.queryCount(form);
		List<MessageConfig> list = iMessageConfigMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public MessageConfig getById(Integer id) {
		MessageConfig bc = null;
		try {
			bc = iMessageConfigMapper.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bc;
	}

	@Override
	public List<MessageConfig> queryAll() {
		List<MessageConfig> list = null;
		try {
			list = iMessageConfigMapper.queryAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
