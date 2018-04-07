package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.constant.ExcelHeaders;
import com.webapps.common.dto.EnrollmentExportDto;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.entity.Enrollment;
import com.webapps.common.entity.UserAwardExchange;
import com.webapps.common.form.UserAwardExchangeRequestForm;
import com.webapps.common.utils.*;
import com.webapps.mapper.IAwardConfigMapper;
import com.webapps.mapper.IUserAwardExchangeMapper;
import com.webapps.service.IUserAwardExchangeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserAwardExchangeImpl implements IUserAwardExchangeService {

    @Autowired
    private IUserAwardExchangeMapper iUserAwardExchangeMapper;

    @Autowired
    private IAwardConfigMapper iAwardConfigMapper;

    @Override
    public Page loadUserAwardExchangeList(Page page, UserAwardExchangeRequestForm form) throws Exception {
        int total = iUserAwardExchangeMapper.queryCount(form);
        int startRows = page.getStartRow();
        int offset = page.getRows();
        List<UserAwardExchange> list = iUserAwardExchangeMapper.queryPage(startRows,offset,form);
        page.setResultList(list);
        page.countRecords(total);
        return page;
    }

    @Override
    public UserAwardExchange getById(Integer id) throws Exception {
        UserAwardExchange uae = iUserAwardExchangeMapper.getById(id);
        return uae;
    }

    @Override
    public ResultDto<UserAwardExchange> saveUserAwardExchange(UserAwardExchange uae) throws Exception {
        List<AwardConfig> acList = iAwardConfigMapper.queryAll();
        if(CollectionUtils.isNotEmpty(acList)){
            List<Double> prop = new ArrayList<>();
            AwardConfig[] acs = new AwardConfig[acList.size()];
            for(int i=0;i<acList.size();i++){
                prop.add(acList.get(i).getPr().doubleValue());
                acs[i] = acList.get(i);
            }
            AliasMethod am = new AliasMethod(prop);
            AwardConfig ac = acs[am.next()];
            uae.setAwardConfigId(ac.getId());
            uae.setAwardName(ac.getName());
        }
        uae.setCreateTime(new Date());
        uae.setDataState(1);
        uae.setStatus(0);
        int result = iUserAwardExchangeMapper.insert(uae);
        ResultDto<UserAwardExchange> dto = new ResultDto<>();
        if(result==1){
            dto.setResult("S");
            dto.setData(uae);
        }else{
            dto.setResult("F");
            dto.setErrorMsg("保存抽奖信息失败");
        }
        return dto;
    }

    @Override
    public ResultDto<String> deleteUserAwardExchangeById(Integer id) throws Exception {
        int result = iUserAwardExchangeMapper.deleteById(id);
        ResultDto<String> dto = new ResultDto<>();
        if(result==1){
            dto.setResult("S");
        }else{
            dto.setResult("S");
            dto.setErrorMsg("删除抽奖信息失败");
        }
        return dto;
    }

    @Override
    public void exportExcel(HttpSession session, HttpServletResponse response, UserAwardExchangeRequestForm form)throws Exception {
        List<UserAwardExchange> list = iUserAwardExchangeMapper.queryUserAwardExchangeForExport(form);
        String filePath = "";
        String excelPath = (String) PropertiesUtil.getProperty("user_award_exchange_excel_path");
        if(CollectionUtils.isNotEmpty(list)){
            filePath = excelPath + File.separator + CommonUtil.getUUID() + ".xls";
            OutputStream out = null;
            try {
                out = new FileOutputStream(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ExcelPoiUtil<EnrollmentExportDto> poi = new ExcelPoiUtil<>();
            poi.exportExcel("进度管理", ExcelHeaders.EXPORT_USER_AWARD_EXCHANGE_HEADERS,
                    ExcelHeaders.EXPORT_USER_AWARD_EXCHANGE_FIELDS, list, out);
            FileUtil.downloadFile(response, filePath);
        }
    }

    @Override
    public List<UserAwardExchange> queryUserAwardExchangeByUserId(Integer userId) throws Exception {
        UserAwardExchangeRequestForm form = new UserAwardExchangeRequestForm();
        form.setUserId(userId);
        List<UserAwardExchange> list = iUserAwardExchangeMapper.queryUserAwardExchangeForExport(form);
        return list;
    }

    @Override
    public List<UserAwardExchange> queryAllUserLottery() throws Exception {
        List<UserAwardExchange> list = iUserAwardExchangeMapper.queryAll();
        return list;
    }

    @Override
    public ResultDto<String> updateExchangeStatusById(int exId) throws Exception {
        UserAwardExchange uaEx = iUserAwardExchangeMapper.getById(exId);
        uaEx.setStatus(1);
        uaEx.setUpdateTime(new Date());
        iUserAwardExchangeMapper.updateById(exId,uaEx);
        ResultDto<String> dto = new ResultDto<>();
        dto.setResult("S");
        return dto;
    }
}
