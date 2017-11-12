package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.form.GroupUserRequestForm;
import com.webapps.mapper.IGroupUserMapper;
import com.webapps.service.IGroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/loadGroupUserPage")
    public Page loadGroupUserPage(Model model, Page page, GroupUserRequestForm form){
        try {
            page = iGroupUserService.loadPage(page,form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
