package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Company;
import com.webapps.common.entity.GroupUser;
import com.webapps.common.form.GroupUserRequestForm;
import com.webapps.mapper.IGroupUserMapper;
import com.webapps.service.IGroupUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupUserServiceImpl implements IGroupUserService {
    @Autowired
    private IGroupUserMapper iGroupUserMapper;

    @Override
    public ResultDto<String> batchInsert(List<GroupUser> list, GroupUser leader) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        if(CollectionUtils.isNotEmpty(list)&&leader!=null){
            Date createTime = new Date();
            for(GroupUser user:list){
                user.setLeaderMobile(leader.getLeaderMobile());
                user.setLeaderName(leader.getLeaderName());
                user.setCreateTime(createTime);
                user.setDataState(1);
            }
            int count = iGroupUserMapper.batchInsert(list);
            if(count==list.size()){
                dto.setResult("S");
                return dto;
            }
            dto.setResult("F");
            dto.setErrorMsg("部分数据保存失败");
            return dto;
        }
        dto.setResult("F");
        dto.setErrorMsg("传入数据为空");
        return dto;
    }

    @Override
    public Page loadPage(Page page, GroupUserRequestForm form) throws Exception {
        int startRow = page.getStartRow();
        int rows = page.getRows();
        int count = iGroupUserMapper.queryCount(form);
        List<GroupUser> list = iGroupUserMapper.queryPage(startRow, rows, form);
        page.setResultList(list);
        page.setRecords(count);
        page.countRecords(count);
        return page;
    }
}
