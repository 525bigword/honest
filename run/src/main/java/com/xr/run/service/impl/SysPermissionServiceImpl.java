package com.xr.run.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.PermissionDao;
import com.xr.run.dao.SysPermissionMapper;
import com.xr.run.entity.SysPermission;
import com.xr.run.service.SysPermissionService;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper,SysPermission> implements SysPermissionService {

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     */
    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = getUserPermissionFromDB(username);
        return userPermission;
    }

    @Override
    public void addSysPermission(SysPermission sysPermission) throws Exception {
        baseMapper.addSysPermission(sysPermission);
    }

    @Override
    public IPage<SysPermission> findSysPermissionPage(Page page, String name, Integer required) throws Exception{
        IPage<SysPermission> sysPermissionPage=null;
        System.out.println(required);
        if(required==3){
            sysPermissionPage = baseMapper.findSysPermissionPage(page, name);
        }else{
            sysPermissionPage = baseMapper.findSysPermissionPageRequired(page, name, required);
        }
        return sysPermissionPage;
    }

    /**
     * 从数据库查询用户权限信息
     */
    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = baseMapper.getUserPermission(username);
        //管理员角色ID为1
        int adminRoleId = 1;
        //如果是管理员
        String roleIdKey = "roleId";
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
            Set<String> menuList = baseMapper.getAllMenu();
            Set<String> permissionList = baseMapper.getAllPermission();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;
    }
}
