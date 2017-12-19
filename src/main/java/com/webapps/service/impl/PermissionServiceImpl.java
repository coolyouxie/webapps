package com.webapps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Permission;
import com.webapps.common.entity.PermissionRelation;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.mapper.IPermissionMapper;
import com.webapps.mapper.IPermissionRelationMapper;
import com.webapps.service.IPermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService{
	
	private static Logger logger = Logger.getLogger(PermissionServiceImpl.class);

    @Autowired
    private IPermissionMapper iPermissionMapper;
    
    @Autowired
    private IPermissionRelationMapper iPermissionRelationMapper;


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
        PermissionRequestForm form = new PermissionRequestForm();
        form.setLevel(3);
        form.setParentCode(p.getCode());
        return p;
    }

    @Override
    public ResultDto<String> saveOperatePermission(Permission permission) throws Exception {
        ResultDto<String> dto =new ResultDto<>();
        if(permission.getId()!=null){
            iPermissionMapper.updateById(permission.getId(),permission);
        }else{
            iPermissionMapper.insert(permission);
        }
        dto.setResult("S");
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

	@Override
	public List<Permission> loadAllOperatePermission() {
		PermissionRequestForm form = new PermissionRequestForm();
		form.setLevel(3);
		try {
			List<Permission> list = iPermissionMapper.queryByConditions(form);
			return list;
		} catch (Exception e) {
			logger.error("查询操作权限异常");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Permission> loadAllMenuPermission() {
		PermissionRequestForm form = new PermissionRequestForm();
		form.setLevel(2);
		try {
			List<Permission> list = iPermissionMapper.queryByConditions(form);
			return list;
		} catch (Exception e) {
			logger.error("查询菜单权限异常");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Permission> loadUserPermission(Integer userId) {
		
		return null;
	}

	@Override
	public ResultDto<String> saveMenuPermission(PermissionRequestForm permission) throws Exception {
		 ResultDto<String> dto =new ResultDto<>();
	        if(permission.getId()!=null){
	            iPermissionMapper.updateById(permission.getId(),permission);
	            List<PermissionRelation> insertList = new ArrayList<>();
	            List<PermissionRelation> deleteList = new ArrayList<>();
	            PermissionRelation pr = new PermissionRelation();
	            pr.setParentPermissionId(permission.getId());
	            List<PermissionRelation> list = iPermissionRelationMapper.queryByConditions(pr);
	            if(CollectionUtils.isEmpty(list)||permission.getChildPermission()==null
	            		||permission.getChildPermission().length==0){
	            	dto.setResult("S");
	            	return dto;
	            }
	            for(PermissionRelation temp:list){
	            	boolean flag = false;
	            	for(int i=0;i<permission.getChildPermission().length;i++){
	            		if(temp.getId().equals(permission.getChildPermission()[i])){
	            			flag = true;
	            		}
	            	}
	            	if(!flag){
	            		deleteList.add(temp);
	            	}
	            }
	            if(CollectionUtils.isNotEmpty(deleteList)){
	            	iPermissionRelationMapper.batchDeleteInLogic(deleteList);
	            }
	            for(int i=0;i<permission.getChildPermission().length;i++){
	            	boolean flag = false;
	            	boolean updateFlag = false;
	            	PermissionRelation relation = new PermissionRelation();
	            	relation.setParentPermissionId(permission.getId());
	            	relation.setPermissionId(permission.getChildPermission()[i]);
	            	relation.setDataState(1);
	            	for(PermissionRelation temp:list){
	            		if(temp.getId().equals(permission.getChildPermission()[i])){
	            			flag = true;
	            			if(temp.getDataState()==0){
	            				updateFlag = true;
	            			}
	            		}
	            	}
	            	if(!flag){
	            		insertList.add(relation);
	            		if(!updateFlag){
	            			relation.setCreateTime(new Date());
	            			iPermissionRelationMapper.insert(relation);
	            		}else{
	            			relation.setUpdateTime(new Date());
	            			iPermissionRelationMapper.updateById(relation.getId(),relation);
	            		}
	            	}
	            }
	        }else{
	            iPermissionMapper.insert(permission);
	            if(permission.getChildPermission()!=null&&permission.getChildPermission().length!=0){
	            	for(int i=0;i<permission.getChildPermission().length;i++){
	            		PermissionRelation relation = new PermissionRelation();
		            	relation.setParentPermissionId(permission.getId());
		            	relation.setPermissionId(permission.getChildPermission()[i]);
		            	relation.setCreateTime(new Date());
		            	relation.setDataState(1);
		            	iPermissionRelationMapper.insert(relation);
	            	}
	            }
	        }
	        dto.setResult("S");
	        return dto;
	}
}
