package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.form.UserAwardExchangeRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IUserAwardExchangeService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

@Controller
@RequestMapping("userAwardExchange")
public class UserAwardExchangeController {

    private static Logger logger = Logger.getLogger(UserAwardExchangeController.class);

    @Autowired
    private IUserAwardExchangeService iUserAwardExchangeService;

    @RequestMapping("/toUserAwardExchangePage")
    public String toUserAwardExchangePage(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "/config/userAwardExchangeList";
    }

    @ResponseBody
    @RequestMapping(value = "/loadUserAwardExchangeList")
    public String loadUserAwardExchangeList(Page page, UserAwardExchangeRequestForm form) {
        try {
            if (form != null && StringUtils.isNotBlank(form.getUserName())) {
                form.setUserName(URLDecoder.decode(form.getUserName(), "UTF-8"));
            }
            if (form != null && StringUtils.isNotBlank(form.getAwardName())) {
                form.setAwardName(URLDecoder.decode(form.getAwardName(), "UTF-8"));
            }
            if (form != null && StringUtils.isNotBlank(form.getEnrollTimeStart())) {
                form.setEnrollTimeStart(form.getEnrollTimeStart() + " 00:00:00");
            }
            if (form != null && StringUtils.isNotBlank(form.getEnrollTimeEnd())) {
                form.setEnrollTimeEnd(form.getEnrollTimeEnd() + " 23:59:59");
            }
            page = iUserAwardExchangeService.loadUserAwardExchangeList(page, form);
            return JSONUtil.toJSONString(JSONObject.fromObject(page));
        } catch (Exception e) {
            logger.error("查询奖品配置信息异常");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/exportExcel", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    public void exportExcel(Model model, HttpSession session, HttpServletResponse response,
                            String userNameForExport, String userMobileForExport, String awardNameForExport,
                            String enrollTimeStartForExport, String enrollTimeEndForExport, int statusForExport) {
        UserAwardExchangeRequestForm form = setCondition(userNameForExport, userMobileForExport, awardNameForExport,
                enrollTimeStartForExport, enrollTimeEndForExport, statusForExport);
        try {
            iUserAwardExchangeService.exportExcel(session, response, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserAwardExchangeRequestForm setCondition(String userName, String userMobile, String awardName,
                                                      String enrollTimeStart, String enrollTimeEnd, int status) {
        UserAwardExchangeRequestForm form = new UserAwardExchangeRequestForm();
        form.setEnrollTimeStart(enrollTimeStart);
        form.setEnrollTimeEnd(enrollTimeEnd);
        form.setUserName(userName);
        form.setUserMobile(userMobile);
        form.setAwardName(awardName);
        form.setStatus(status);
        return form;
    }

}
