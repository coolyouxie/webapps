package com.webapps.service;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.GroupUser;
import com.webapps.common.form.GroupUserRequestForm;

import java.util.List;

public interface IGroupUserService {

    public ResultDto<String> batchInsert(List<GroupUser> list,GroupUser leader)throws Exception;

    public Page loadPage(Page page, GroupUserRequestForm form)throws Exception;

}
