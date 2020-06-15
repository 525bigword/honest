package com.xr.run.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 查询用户的角色 菜单 权限
     */
    @Select(" SELECT u.sid userId,u.name nickname,u.pid roleId,r.pname roleName,p.menu_code menuCode," +
            "p.permission_code permissionCode FROM sys_staff u " +
            "LEFT JOIN sys_post r ON r.pid = u.pid " +
            "LEFT JOIN sys_post_permission rp ON u.pid = rp.postid " +
            "LEFT JOIN sys_permission p ON rp.pid = p.id " +
            "WHERE u.username = #{username} " +
            "AND u.staus = '1'")
    JSONObject getUserPermission(String username);

    /**
     * 查询所有的菜单
     */
    @Select("SELECT p.menu_code menuCode FROM sys_permission p ORDER BY p.id")
    Set<String> getAllMenu();

    /**
     * 查询所有的权限
     */
    @Select("SELECT p.permission_code permissionCode FROM sys_permission p ORDER BY p.id")
    Set<String> getAllPermission();
    /**
     * 分页查询权限
     */
    @Select("select id,menu_code,menu_name,permission_code,permission_name,required_permission from sys_permission where menu_name like CONCAT('%',#{name},'%') and required_permission=#{required}")
    IPage<SysPermission> findSysPermissionPageRequired(Page page, @Param("name")String name,@Param("required")Integer required);
    /**
     * 分页查询权限
     */
    @Select("select id,menu_code,menu_name,permission_code,permission_name,required_permission from sys_permission where menu_name like CONCAT('%',#{name},'%')")
    IPage<SysPermission> findSysPermissionPage(Page page, @Param("name")String name);
    @Insert("insert into sys_permission values(null,#{menu_code},#{menu_name},#{permission_code},#{permission_name},#{required_permission})")
    void addSysPermission(SysPermission sysPermission);
}
