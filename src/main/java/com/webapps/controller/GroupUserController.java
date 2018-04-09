package com.webapps.controller;

import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.webapps.common.bean.Page;
import com.webapps.common.form.GroupUserRequestForm;
import com.webapps.service.IGroupUserService;

@Controller
@RequestMapping(value = "/groupUser")
public class GroupUserController {

    @Autowired
    private IGroupUserService iGroupUserService;

    @RequestMapping(value = "/toGroupUserListPage")
    public String toGroupUserListPage(Model model){
        return "/groupUser/groupUserList";
    }

    @ResponseBody
    @RequestMapping(value = "/loadGroupUserPage",produces = "text/html;charset=UTF-8")
    public String loadGroupUserPage(Model model, Page page, GroupUserRequestForm form){
        try {
            if(form!=null&& StringUtils.isNotBlank(form.getLeaderName())){
                form.setLeaderName(URLDecoder.decode(form.getLeaderName(),"UTF-8"));
            }
            page = iGroupUserService.loadPage(page,form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page);
    }
}
