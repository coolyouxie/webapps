package com.webapps.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.BannerConfig;
import com.webapps.common.entity.Entity;
import com.webapps.common.entity.OperateLog;
import com.webapps.common.form.BannerConfigRequestForm;
import com.webapps.mapper.IBannerConfigMapper;
import com.webapps.mapper.IOperateLogMapper;
import com.webapps.service.IBannerConfigService;

@Service
@Transactional
public class BannerConfigServiceImpl implements IBannerConfigService {
	
	private static Logger logger = Logger.getLogger(BannerConfigServiceImpl.class);
	
	@Autowired
	private IBannerConfigMapper iBannerConfigMapper;
	
	@Autowired
	private IOperateLogMapper iOperateLogMapper;

	@Override
	public ResultDto<BannerConfig> saveBannerConfig(BannerConfigRequestForm form) {
		int result = 0;
		String errorMsg = "";
		ResultDto<BannerConfig> dto = new ResultDto<BannerConfig>();
		int type = 0;
		String remark = null;
		if(form.getId()!=null){
			try {
				result = iBannerConfigMapper.updateById(form.getId(), form);
				errorMsg = "更新失败，请稍后再试";
				type = 2;
				remark = "更新Banner数据";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			result = iBannerConfigMapper.insert(form);
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
	public int deleteBannerConfigById(BannerConfigRequestForm form) {
		int count = 0;
		String errorMsg = null;
		String remark = "删除Banner数据";
		String result = null;
		try {
			count = iBannerConfigMapper.deleteByIdInLogic(form.getId());
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
	public Page loadBannerConfigList(Page page, BannerConfigRequestForm form) {
		int startRow = page.getStartRow();
		int endRow = page.getEndRow();
		int count = iBannerConfigMapper.queryCount(form);
		List<BannerConfig> list = iBannerConfigMapper.queryPage(startRow, endRow, form);
		page.setResultList(list);
		page.setRecords(count);
		return page;
	}

	@Override
	public BannerConfig getById(Integer id) {
		BannerConfig bc = null;
		try {
			bc = iBannerConfigMapper.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bc;
	}

	@Override
	public List<BannerConfig> queryAll() {
		List<BannerConfig> list = null;
		try {
			list = iBannerConfigMapper.queryAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
