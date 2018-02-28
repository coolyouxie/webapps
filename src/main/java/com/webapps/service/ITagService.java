package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.RecruitmentTag;
import com.webapps.common.entity.Tag;
import com.webapps.common.entity.User;
import com.webapps.common.form.BillRecordRequestForm;

import java.math.BigDecimal;
import java.util.List;

public interface ITagService {
	
	List<Tag> queryAllTags()throws Exception;

	List<RecruitmentTag> queryAllRecruitmentTags(Integer recruitmentId)throws Exception;

	List<Tag> queryAllTagsWithCheckStatus(Integer recruitmentId)throws Exception;
}
