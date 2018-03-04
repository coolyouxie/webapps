package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.AwardConfig;
import com.webapps.common.form.AwardConfigRequestForm;
import com.webapps.mapper.IAwardConfigMapper;
import com.webapps.service.IAwardConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AwardConfigImpl implements IAwardConfigService{

    @Autowired
    private IAwardConfigMapper iAwardConfigMapper;

    @Override
    public Page loadPageList(Page page, AwardConfigRequestForm form) throws Exception {
        int offset = page.getRows();
        int startRow = page.getStartRow();
        int total = iAwardConfigMapper.queryCount(form);
        List<AwardConfig> list = iAwardConfigMapper.queryPage(startRow,offset,form);
        page.setRecords(total);
        page.setResultList(list);
        return page;
    }

    @Override
    public ResultDto<String> addAwardConfig(AwardConfig config) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        iAwardConfigMapper.insert(config);
        dto.setResult("S");
        return dto;
    }

    @Override
    public ResultDto<String> deleteAwardConfigById(Integer id) throws Exception {
        int result = iAwardConfigMapper.deleteByIdInLogic(id);
        ResultDto<String> dto = new ResultDto<>();
        if (result==1){
            dto.setResult("S");
            return dto;
        }
        dto.setResult("F");
        dto.setErrorMsg("删除奖品配置信息失败，请稍后重试");
        return dto;
    }

    @Override
    public List<AwardConfig> queryAllAwardConfig() throws Exception {
        List<AwardConfig> list = iAwardConfigMapper.queryAll();
        return list;
    }
}
