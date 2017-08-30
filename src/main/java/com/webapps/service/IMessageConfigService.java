package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.MessageConfig;
import com.webapps.common.form.MessageConfigRequestForm;

public interface IMessageConfigService {
	
	public ResultDto<MessageConfig> saveMessageConfig(MessageConfigRequestForm form);
	
	public int deleteMessageConfigById(MessageConfigRequestForm form);
	
	public Page loadMessageConfigList(Page page,MessageConfigRequestForm form);
	
	public MessageConfig getById(Integer id);
	
	public List<MessageConfig> queryAll();
	
}
