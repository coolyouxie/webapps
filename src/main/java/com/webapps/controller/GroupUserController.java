package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.form.GroupUserRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.mapper.IGroupUserMapper;
import com.webapps.service.IGroupUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;

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
        return JSONUtil.toJSONString(JSONObject.fromObject(page));
    }
}
