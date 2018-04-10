package com.webapps.controller;

import com.alibaba.fastjson.JSONObject;
import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Agency;
import com.webapps.common.entity.Province;
import com.webapps.common.form.AgencyRequestForm;
import com.webapps.common.utils.HttpUtil;
import com.webapps.common.utils.PropertiesUtil;
import com.webapps.service.IAgencyService;
import com.webapps.service.IProvinceService;
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
            return JSONObject.toJSONString(page);
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

    @ResponseBody
    @RequestMapping("/saveAgency")
    public String saveAgency(Model model, AgencyRequestForm agency, HttpServletRequest request, HttpServletResponse response) {
    	if (null != agency) {
            ResultDto<String> dto = null;
            mapParam.put("address",agency.getProvinceName()+agency.getCityName()+agency.getAreaName()+agency.getAddress());
            com.alibaba.fastjson.JSONObject result = HttpUtil.doGet(mapUrl, mapParam);
            if(result!=null){
                String responseStr = result.getString("response");
                responseStr = responseStr.substring(responseStr.lastIndexOf("(")+1,responseStr.length()-1);
                if(StringUtils.isNotBlank(responseStr)){
                    com.alibaba.fastjson.JSONObject responseObj = com.alibaba.fastjson.JSONObject.parseObject(responseStr);
                    if(responseObj!=null){
                        com.alibaba.fastjson.JSONObject resultObj = responseObj.getJSONObject("result");
                        if(resultObj!=null){
                            com.alibaba.fastjson.JSONObject location = resultObj.getJSONObject("location");
                            agency.setLongitude(Double.valueOf(location.getString("lng")));
                            agency.setLatitude(Double.valueOf(location.getString("lat")));
                        }
                    }
                }
            }
            try {
                dto = iAgencyService.saveAgency(agency);
                return JSONObject.toJSONString(dto);
            } catch (Exception e) {
                logger.error("保存门店信息时异常");
                e.printStackTrace();
                dto = new ResultDto<>();
                dto.setErrorMsg("保存门店信息异常");
                dto.setResult("F");
                return JSONObject.toJSONString(dto);
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
            return JSONObject.toJSONString(dto);
        } catch (Exception e) {
            logger.error("删除门店信息时异常，请稍后再试");
            e.printStackTrace();
            dto = new ResultDto<>();
            dto.setErrorMsg("删除门店信息时异常，请稍后再试");
            dto.setResult("F");
            return JSONObject.toJSONString(dto);
        }
    }

    @ResponseBody
    @RequestMapping(value="/loadAgencyByDistrictId")
    public String loadAgencyByDistrictId(Model model,AgencyRequestForm agency){
		try {
			ResultDto<List<Agency>> list = iAgencyService.queryAllAgencyBy(agency);
			return com.alibaba.fastjson.JSONObject.toJSONString(list);
		} catch (Exception e) {
			logger.error("加载门店信息异常");
			e.printStackTrace();
		}
    	return null;
    }

}
