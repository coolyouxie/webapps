package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.User;
import com.webapps.common.form.EnrollmentRequestForm;
import com.webapps.common.form.RateDtoRequestForm;
import com.webapps.common.utils.JSONUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.mapper.IEnrollmentMapper;
import com.webapps.service.IEnrollmentService;
import com.webapps.service.IRateService;
import com.webapps.service.IUserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("recruitProcess")
public class RecruitProcessController {

    @Autowired
    private IRateService iRateService;

    @Autowired
    private IEnrollmentService iEnrollmentService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/toRecruitProcessListPage")
    public String toRecruitProcessListPage(Model model,HttpServletRequest request, HttpServletResponse response) {
        String intentionCities = (String) PropertiesUtil.getProperty("intentionCities");
        String[] cities = intentionCities.split(",");
        List<JSONObject> list = new ArrayList<JSONObject>();
        getIntentionCities(cities, list);
        List<User> list1 = iUserService.queryUserByType(4);
        model.addAttribute("talkers",list1);
        model.addAttribute("intentionCities",list);
        return "/recruitProcess/recruitProcessList";
    }

    static void getIntentionCities(String[] cities, List<JSONObject> list) {
        for (int i=0;i<cities.length;i++){
            String[] array = cities[i].split("\\:");
            JSONObject obj = new JSONObject();
            obj.put("id",array[0]);
            obj.put("city",array[1]);
            list.add(obj);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/loadRecruitProcessList", produces = "text/html;charset=UTF-8")
    public String loadRecruitProcessList(Model model, Page page, EnrollmentRequestForm form) {
        try {
            if (form != null && StringUtils.isNotBlank(form.getTalkerName())) {
                form.setTalkerName(URLDecoder.decode(form.getTalkerName(), "UTF-8"));
            }
            if (form != null&&form.getCompany()!=null && StringUtils.isNotBlank(form.getCompany().getName())) {
                form.getCompany().setName(URLDecoder.decode(form.getCompany().getName(), "UTF-8"));
            }
            if (form != null&&form.getUser()!=null && StringUtils.isNotBlank(form.getUser().getName())) {
                form.getUser().setName(URLDecoder.decode(form.getUser().getName(), "UTF-8"));
            }
            page = iEnrollmentService.loadRecruitProcess(page, form);
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

}
