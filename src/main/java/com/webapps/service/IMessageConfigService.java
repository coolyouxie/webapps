package com.webapps.service;

import java.util.List;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.MessageConfig;
import com.webapps.common.form.MessageConfigRequestForm;

public interface IMessageConfigService {
	
	ResultDto<MessageConfig> saveMessageConfig(MessageConfigRequestForm form);
	
	int deleteMessageConfigById(MessageConfigRequestForm form);
	
	Page loadMessageConfigList(Page page, MessageConfigRequestForm form);
	
	MessageConfig getById(Integer id);
	
	List<MessageConfig> queryAll();
	
}
