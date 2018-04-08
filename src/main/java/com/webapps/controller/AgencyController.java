package com.webapps.controller;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Agency;
import com.webapps.common.entity.Province;
import com.webapps.common.form.AgencyRequestForm;
import com.webapps.common.utils.HttpUtil;
import com.webapps.common.utils.JSONUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.service.IAgencyService;
import com.webapps.service.IProvinceService;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("agency")
public class AgencyController {

    private static Logger logger = Logger.getLogger(AgencyController.class);

    @Autowired
    private IAgencyService iAgencyService;

    @Autowired
    private IProvinceService iProvinceService;
    
    private static com.alibaba.fastjson.JSONObject mapParam = new com.alibaba.fastjson.JSONObject();
    
    private final String mapUrl = (String)PropertiesUtil.getProperty("map_api_url");
    
    static{
    	mapParam.put("output", "json");
    	String ak = (String) PropertiesUtil.getProperty("map_api_ak");
    	mapParam.put("ak", ak);
    	mapParam.put("callback", "showLocation");
    }

    @RequestMapping("/toAgencyListPage")
    public String toAgencyListPage(Model model) {
        getProvinceList(model);
        return "/agency/agencyList";
    }

    private void getProvinceList(Model model) {
        ResultDto<List<Province>> dto = iProvinceService.queryProvinceByParentId(0);
        if(dto!=null&&"S".equals(dto.getResult())){
            model.addAttribute("provinces",dto.getData());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/loadAgencyList", produces = "text/html;charset=UTF-8")
    public String loadAgencyList(Model model, Page page, AgencyRequestForm form) {
        try {
            if (form != null && StringUtils.isNotBlank(form.getName())) {
                form.setName(URLDecoder.decode(form.getName(), "UTF-8"));
            }
            page = iAgencyService.loadAgencyList(page, form);
            return JSONUtil.toJSONString(JSONObject.fromObject(page));
        } catch (Exception e) {
            logger.error("加载门店信息列表异常");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/toAgencyInfoPage")
    public String toAgencyInfoPage(Model model, String type, Integer id, HttpServletRequest request, HttpServletResponse response) {
        //如果是新增直跳转页面
        getProvinceList(model);
        if ("add".equals(type)) {
            return "/agency/addAgency";
        }
        if ("edit".equals(type)) {
            try {
                Agency agency = getAgencyById(id);
                model.addAttribute("agency", agency);
                return "/agency/editAgency";
            } catch (Exception e) {
                logger.error("查询门店信息时异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/saveAgency")
    public String saveAgency(Model model, AgencyRequestForm agency, HttpServletRequest request, HttpServletResponse response) {
    	if (null != agency) {
            ResultDto<String> dto = null;
            com.alibaba.fastjson.JSONObject result = HttpUtil.doGet(mapUrl, mapParam);
            if(result!=null){
            	
            }
            try {
                dto = iAgencyService.saveAgency(agency);
                return JSONUtil.toJSONString(JSONObject.fromObject(dto));
            } catch (Exception e) {
                logger.error("保存门店信息时异常");
                e.printStackTrace();
                dto = new ResultDto<>();
                dto.setErrorMsg("保存门店信息异常");
                dto.setResult("F");
                return JSONUtil.toJSONString(JSONObject.fromObject(dto));
            }
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/deleteAgencyById")
    public String deleteAgencyById(Model model, Integer id, HttpServletRequest request, HttpServletResponse response) {
        ResultDto<String> dto = null;
        try {
            dto = iAgencyService.deleteAgencyById(id);
            return JSONUtils.valueToString(JSONObject.fromObject(dto));
        } catch (Exception e) {
            logger.error("删除门店信息时异常，请稍后再试");
            e.printStackTrace();
            dto = new ResultDto<>();
            dto.setErrorMsg("删除门店信息时异常，请稍后再试");
            dto.setResult("F");
            return JSONUtils.valueToString(JSONObject.fromObject(dto));
        }
    }

    private Agency getAgencyById(Integer id) {
        try {
            Agency agency = iAgencyService.getById(id);
            return agency;
        } catch (Exception e) {
            logger.error("查询门店信息时异常");
            e.printStackTrace();
        }
        return null;
    }

}
