package com.xr.run.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    //根据栏目名查询数量
    @Select("select count(id) from sys_permission where permission_code=#{permissionCode}")
    Integer findSysPermissionCountByMenuCodeOrPermissionCode(@Param("permissionCode") String permissionCode);
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
     * 查询用户信息
     */
    @Select("SELECT sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid,(select pname from sys_post where pid=sys_staff.pid) postname, create_time,create_id,staus FROM sys_staff WHERE username = #{username} and staus = 1")
    SysStaff getSysStaffByUserName(String username);
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
    @Select("select id,menu_code,menu_name,permission_code,permission_name,required_permission from sys_permission where menu_name like CONCAT('%',#{name},'%') and required_permission=#{required} order by id desc")
    IPage<SysPermission> findSysPermissionPageRequired(Page page, @Param("name")String name,@Param("required")Integer required);
    /**
     * 分页查询权限
     */
    @Select("select id,menu_code,menu_name,permission_code,permission_name,required_permission from sys_permission where menu_name like CONCAT('%',#{name},'%') order by id desc")
    IPage<SysPermission> findSysPermissionPage(Page page, @Param("name")String name);
    @Insert("insert into sys_permission values(null,#{p.menuCode},#{p.menuName},#{p.permissionCode},#{p.permissionName},#{p.requiredPermission})")
    void addSysPermission(@Param("p") SysPermission sysPermission);
    /**
     * 查询是否存在该权限码
     */
    @Select("select count(id) from sys_permission where menu_code=#{p.menuCode} and permission_code=#{p.permissionCode}")
    Integer findSysPermissionByMenuCodeAndPermisiionCode(@Param("p") SysPermission sysPermission);
    /**
     * 按menu_name查询相关权限
     */
    @Select("select count(id) from sys_permission where menu_name=#{menuName}")
    Integer findSysPermissionByMenuNametoCount(@Param("menuName")String menuName);
    /**
     * 根据Id删除权限
     */
    @Delete("delete from sys_permission where id=#{id}")
    void delSysPermission(@Param("id") Integer id);
    /**
     * 修改
     */
    @Update("update sys_permission set menu_code=#{menuCode},menu_name=#{menuName},permission_code=#{permissionCode},permission_name=#{permissionName},required_permission=#{requiredPermission} where id=#{id} ")
    void upSysPermission(SysPermission sysPermission);
    //根据Id查询
    @Select("select menu_code from sys_permission where id in ${ids} and permission_name='列表'")
    Set<String> findSysPermissionNameById(@Param("ids") String ids);
    @Select("SELECT permission_code permissionCode FROM sys_permission where id in ${ids} ORDER BY id")
    Set<String> getAllPermissionById(@Param("ids") String ids);
}
