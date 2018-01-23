package com.webapps.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.User;
import com.webapps.common.form.RateDtoRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.service.IRateService;

import net.sf.json.JSONObject;

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

    @RequestMapping(value="/toStatisticsListPage")
    public String toStatisticsListPage(Model model,Integer talkerId,int type){
        model.addAttribute("talkerId",talkerId);
        if(type==1){
            return "/statistics/entryStatisticsList";
        }else if(type==2){
            return "/statistics/expireStatisticsList";
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/loadEntryStatisticsList", produces = "text/html;charset=UTF-8")
    public String loadEntryStatisticsList(Model model,Page page,RateDtoRequestForm form){
        try {
            page = iRateService.loadEntryStatisticsList(page,form);
            return JSONUtil.toJSONString(JSONObject.fromObject(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/loadExpireStatisticsList", produces = "text/html;charset=UTF-8")
    public String loadExpireStatisticsList(Model model,Page page,RateDtoRequestForm form){
        try {
            form.setType(1);
            page = iRateService.loadExpireStatisticsList(page,form);
            return JSONUtil.toJSONString(JSONObject.fromObject(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/exportStatistics", method = RequestMethod.GET)
    public void exportEntryStatistics(Model model,HttpSession session,
    		HttpServletResponse response,int talkerId,int state,int type){
    	iRateService.exportStatistics(session,response,talkerId,state,type);
    }

}
