package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Permission;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.mapper.IPermissionMapper;
import com.webapps.service.IPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService{

    @Autowired
    private IPermissionMapper iPermissionMapper;


    @Override
    public Page loadPermissionList(Page page, PermissionRequestForm form) throws Exception {
        int startRow = page.getStartRow();
        int offset = page.getRows();
        int total = iPermissionMapper.queryCount(form);
        List<Permission> list = iPermissionMapper.queryPage(startRow,offset,form);
        page.setRecords(total);
        page.countRecords(total);
        page.setResultList(list);
        return page;
    }

    @Override
    public Permission getById(Integer id) throws Exception {
        Permission p = iPermissionMapper.getById(id);
        return p;
    }

    @Override
    public ResultDto<Permission> savePermission(Permission permission) throws Exception {
        ResultDto<Permission> dto =new ResultDto<Permission>();
        if(permission.getId()!=null){
            iPermissionMapper.updateById(permission.getId(),permission);
        }else{
            iPermissionMapper.insert(permission);
        }
        dto.setResult("S");
        dto.setData(permission);
        return dto;
    }

    @Override
    public ResultDto<Permission> deletePermissionById(Integer id) throws Exception {
        return null;
    }

    @Override
    public ResultDto<String> validatePermission(PermissionRequestForm form) throws Exception {
        List<Permission> list = iPermissionMapper.getByNameOrCode(form.getName(),form.getCode());
        String errorMsg = "";
        ResultDto<String> dto = new ResultDto<String>();
        dto.setResult("S");
        if(CollectionUtils.isNotEmpty(list)){
            for(int i=0;i<list.size();i++){
                if(form.getName().equalsIgnoreCase(list.get(0).getName())){
                    if(errorMsg.equalsIgnoreCase("")){
                        errorMsg += "权限名称重复";
                    }else{
                        errorMsg += ",权限名称重复";
                    }
                    dto.setResult("F");
                }else{
                    if(errorMsg.equalsIgnoreCase("")){
                        errorMsg += "权限编号重复";
                    }else{
                        errorMsg += ",权限编号重复";
                    }
                    dto.setResult("F");
                }
            }
            dto.setErrorMsg(errorMsg);
        }
        return dto;
    }

    @Override
    public List<Permission> queryByConditions(PermissionRequestForm form) throws Exception {
        List<Permission> list = iPermissionMapper.queryByConditions(form);
        return list;
    }
}
