package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.PromotionConfig;
import com.webapps.common.form.PromotionConfigRequestForm;
import com.webapps.mapper.IPromotionConfigMapper;
import com.webapps.service.IPromotionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PromotionConfigImpl implements IPromotionConfigService{

    @Autowired
    private IPromotionConfigMapper iPromotionConfigMapper;

    @Override
    public Page loadPageList(Page page, PromotionConfigRequestForm form) throws Exception {
        int offset = page.getRows();
        int startRow = page.getStartRow();
        int total = iPromotionConfigMapper.queryCount(form);
        List<PromotionConfig> list = iPromotionConfigMapper.queryPage(startRow,offset,form);
        page.countRecords(total);
        page.setResultList(list);
        return page;
    }

    @Override
    public ResultDto<PromotionConfig> addPromotionConfig(PromotionConfig config) throws Exception {
        ResultDto<PromotionConfig> dto = new ResultDto<>();
        iPromotionConfigMapper.insert(config);
        dto.setResult("S");
        dto.setData(config);
        return dto;
    }

    @Override
    public ResultDto<String> deletePromotionConfigById(Integer id) throws Exception {
        int result = iPromotionConfigMapper.deleteByIdInLogic(id);
        ResultDto<String> dto = new ResultDto<>();
        if (result==1){
            dto.setResult("S");
            return dto;
        }
        dto.setResult("F");
        dto.setErrorMsg("删除活动信息失败，请稍后重试");
        return dto;
    }

    @Override
    public List<PromotionConfig> queryAllPromotionConfig() throws Exception {
        List<PromotionConfig> list = iPromotionConfigMapper.queryAll();
        return list;
    }

    @Override
    public ResultDto<String> updateStatusById(Integer id,int status) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        iPromotionConfigMapper.updateStatusById(id,status);
        dto.setResult("S");
        return dto;
    }

    @Override
    public ResultDto<String> updatePromotionConfig(PromotionConfigRequestForm form) throws Exception {
        ResultDto<String> dto = new ResultDto<String>();
        iPromotionConfigMapper.updateById(form.getId(),form);
        dto.setResult("S");
        return dto;
    }

    @Override
    public PromotionConfig getById(Integer id) throws Exception {
        PromotionConfig pc = iPromotionConfigMapper.getById(id);
        return pc;
    }

    @Override
    public ResultDto<String> updateStatusDate(PromotionConfigRequestForm form) throws Exception {
        iPromotionConfigMapper.updateStatusDate(form);
        ResultDto<String> dto = new ResultDto<>();
        dto.setResult("S");
        return dto;
    }

	@Override
	public List<PromotionConfig> queryPromotionConfig(PromotionConfigRequestForm form) throws Exception {
		List<PromotionConfig> list = iPromotionConfigMapper.queryListByCondition(form);
		return list;
	}

}
