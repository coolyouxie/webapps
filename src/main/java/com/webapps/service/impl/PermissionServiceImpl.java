package com.webapps.service.impl;

import com.webapps.common.bean.Page;
import com.webapps.common.bean.ResultDto;
import com.webapps.common.entity.Permission;
import com.webapps.common.entity.PermissionRelation;
import com.webapps.common.entity.UserPermission;
import com.webapps.common.form.PermissionRequestForm;
import com.webapps.mapper.IPermissionMapper;
import com.webapps.mapper.IPermissionRelationMapper;
import com.webapps.mapper.IUserPermissionMapper;
import com.webapps.service.IPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    private static Logger logger = Logger.getLogger(PermissionServiceImpl.class);

    @Autowired
    private IPermissionMapper iPermissionMapper;

    @Autowired
    private IPermissionRelationMapper iPermissionRelationMapper;

    @Autowired
    private IUserPermissionMapper iUserPermissionMapper;


    @Override
    public Page loadPermissionList(Page page, PermissionRequestForm form) throws Exception {
        int startRow = page.getStartRow();
        int offset = page.getRows();
        int total = iPermissionMapper.queryCount(form);
        List<Permission> list = iPermissionMapper.queryPage(startRow, offset, form);
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
        return p;
    }

    @Override
    public ResultDto<String> saveOperatePermission(Permission permission) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        if (permission.getId() != null) {
            iPermissionMapper.updateById(permission.getId(), permission);
        } else {
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
        List<Permission> list = iPermissionMapper.getByNameOrCode(form.getName(), form.getCode());
        String errorMsg = "";
        ResultDto<String> dto = new ResultDto<>();
        dto.setResult("S");
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(0).getId().equals(form.getId())) {
                    //如果是更新操作时，则判断id是否相等，如果相等，则不校验，否则校验
                    continue;
                }
                if (form.getName().equalsIgnoreCase(list.get(0).getName())) {
                    if (errorMsg.equalsIgnoreCase("")) {
                        errorMsg += "权限名称重复";
                    } else {
                        errorMsg += ",权限名称重复";
                    }
                    dto.setResult("F");
                } else {
                    if (errorMsg.equalsIgnoreCase("")) {
                        errorMsg += "权限编号重复";
                    } else {
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
    public Permission getMenuPermissionById(Integer id) {
        try {
            Permission pr = iPermissionMapper.getById(id);
            if (pr != null) {
                List<Permission> list = iPermissionMapper.queryAllLevel3PermByParentId(id);
                pr.setChildPermissions(list);
                return pr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultDto<String> saveMenuPermission(PermissionRequestForm permission) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        if (permission.getId() != null) {
            iPermissionMapper.updateById(permission.getId(), permission);
            List<PermissionRelation> deleteList = new ArrayList<>();
            PermissionRelation pr = new PermissionRelation();
            pr.setParentPermissionId(permission.getId());
            pr.setLevel(3);
            List<PermissionRelation> list = iPermissionRelationMapper.queryByConditions(pr);
            if (permission.getChildPermission() == null || permission.getChildPermission().length == 0) {
                if (CollectionUtils.isNotEmpty(list)) {
                    iPermissionRelationMapper.batchDeleteInLogic(list);
                    dto.setResult("S");
                    return dto;
                }
            }
            if (CollectionUtils.isEmpty(list)) {
                if (permission.getChildPermission() != null && permission.getChildPermission().length != 0) {
                    savePermissionRelation(permission);
                }
                dto.setResult("S");
                return dto;
            }
            for (PermissionRelation temp : list) {
                boolean flag = false;
                for (int i = 0; i < permission.getChildPermission().length; i++) {
                    if (temp.getPermissionId().equals(permission.getChildPermission()[i])) {
                        flag = true;
                    }
                }
                if (!flag) {
                    deleteList.add(temp);
                }
            }
            if (CollectionUtils.isNotEmpty(deleteList)) {
                //权限关系删除后，用户所拥有的权限关系也需要删除
                iPermissionRelationMapper.batchDelete(deleteList);
                for(PermissionRelation del:deleteList){
                    iUserPermissionMapper.deleteByPermissionRelationId(del.getId());
                }
            }
            for (int i = 0; i < permission.getChildPermission().length; i++) {
                boolean flag = false;
                boolean updateFlag = false;
                PermissionRelation relation = new PermissionRelation();
                relation.setParentPermissionId(permission.getId());
                relation.setPermissionId(permission.getChildPermission()[i]);
                relation.setDataState(1);
                for (PermissionRelation temp : list) {
                    if (temp.getPermissionId().equals(permission.getChildPermission()[i])) {
                        flag = true;
                        if (temp.getDataState() == 0) {
                            updateFlag = true;
                        }
                    }
                }
                if (!flag) {
                    if (!updateFlag) {
                        relation.setCreateTime(new Date());
                        iPermissionRelationMapper.insert(relation);
                    } else {
                        relation.setUpdateTime(new Date());
                        iPermissionRelationMapper.updateById(relation.getId(), relation);
                    }
                }
            }
        } else {
            //保存权限
            iPermissionMapper.insert(permission);
            //菜单权限保存到权限关系表
            PermissionRelation pr = new PermissionRelation();
            pr.setPermissionId(permission.getId());
            pr.setParentPermissionId(0);
            pr.setCreateTime(new Date());
            pr.setDataState(1);
            iPermissionRelationMapper.insert(pr);
            if (permission.getChildPermission() != null && permission.getChildPermission().length != 0) {
                savePermissionRelation(permission);
            }
        }
        dto.setResult("S");
        return dto;
    }

    private void savePermissionRelation(PermissionRequestForm permission) {
        for (int i = 0; i < permission.getChildPermission().length; i++) {
            PermissionRelation relation = new PermissionRelation();
            relation.setParentPermissionId(permission.getId());
            relation.setPermissionId(permission.getChildPermission()[i]);
            relation.setCreateTime(new Date());
            relation.setDataState(1);
            iPermissionRelationMapper.insert(relation);
        }
    }

    @Override
    public List<PermissionRelation> loadAllPermissions(Integer userId) {
        PermissionRelation pr = new PermissionRelation();
        pr.setLevel(2);
        try {
            List<PermissionRelation> menuList = iPermissionRelationMapper.queryByConditions(pr);
            List<PermissionRelation> operateList = iPermissionRelationMapper.queryPermissionRelationWithUserCheckFlag(userId);
            for (PermissionRelation menu : menuList) {
                List<Permission> list = new ArrayList<>();
                for (PermissionRelation operate : operateList) {
                    if (operate.getParentPermissionId().equals(menu.getpId())) {
                        list.add(operate);
                    }else if(operate.getId().equals(menu.getId())){
                        menu.setCheckFlag(operate.getCheckFlag());
                    }
                }
                menu.setChildPermissions(list);
            }
            return menuList;
        } catch (Exception e) {
            logger.error("查询菜单及操作权限异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultDto<String> saveUserPermisson(Integer userId, int[] menuId, int[] operateId) throws Exception {
        ResultDto<String> dto = new ResultDto<>();
        saveUserPermissions(userId, menuId,2);
        saveUserPermissions(userId, operateId,3);
        dto.setResult("S");
        return dto;
    }

    private void saveUserPermissions(Integer userId, int[] permissionRelationId,int level) {
        UserPermission condition = new UserPermission();
        condition.setLevel(level);
        condition.setDataState(1);
        condition.setUserId(userId);
        try {
            List<UserPermission> list = iUserPermissionMapper.queryByConditions(condition);
            //如果该用户还没有可用权限，则直接插入权限
            if(CollectionUtils.isEmpty(list)){
                if(permissionRelationId!=null&&permissionRelationId.length>0){
                    for(int i=0;i<permissionRelationId.length;i++){
                        UserPermission up = new UserPermission();
                        up.setPermissionRelationId(permissionRelationId[i]);
                        up.setUserId(userId);
                        up.setCreateTime(new Date());
                        up.setDataState(1);
                        iUserPermissionMapper.insert(up);
                    }
                }
                return;
            }
            List<UserPermission> deleteList = new ArrayList<>();
            for(UserPermission temp:list){
                boolean delFlag = false;
                for(int i=0;i<permissionRelationId.length;i++){
                    if(temp.getId().equals(permissionRelationId[i])){
                        delFlag = true;
                        break;
                    }
                }
                if(!delFlag){
                    deleteList.add(temp);
                }
            }
            if(CollectionUtils.isNotEmpty(deleteList)){
                //物理删除数据
                iUserPermissionMapper.batchDelete(deleteList);
            }
            for(int i=0;i<permissionRelationId.length;i++){
                boolean insertFlag = false;
                for(UserPermission temp:list){
                    if(temp.getId().equals(permissionRelationId[i])){
                        insertFlag = true;
                        break;
                    }
                }
                //如果页面传入的权限关系ID不在该用户对应的用户权限关系中，则插入数据库
                if(!insertFlag){
                    UserPermission up = new UserPermission();
                    up.setDataState(1);
                    up.setCreateTime(new Date());
                    up.setPermissionRelationId(permissionRelationId[i]);
                    up.setUserId(userId);
                    iUserPermissionMapper.insert(up);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PermissionRelation>  loadUserPermissions(Integer userId)throws Exception{
        List<PermissionRelation> userPermissionList = iPermissionRelationMapper.queryPermissionRelationWithUserCheckFlag(userId);
        return userPermissionList;
    }
}
