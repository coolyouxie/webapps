package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.form.RateDtoRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IPictureService;
import com.webapps.service.IRateService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Controller
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private IRateService iRateService;

    @RequestMapping("/toRateListPage")
    public String toRateListPage(HttpServletRequest request, HttpServletResponse response) {
        return "/statistics/rateList";
    }

    @ResponseBody
    @RequestMapping(value = "/loadRateList", produces = "text/html;charset=UTF-8")
    public String loadRateList(Model model, Page page, RateDtoRequestForm form) {
        try {
            if (form != null && StringUtils.isNotBlank(form.getTalkerName())) {
                form.setTalkerName(URLDecoder.decode(form.getTalkerName(), "UTF-8"));
            }
            page = iRateService.loadRateDtoList(page, form);
            return JSONUtil.toJSONString(JSONObject.fromObject(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
