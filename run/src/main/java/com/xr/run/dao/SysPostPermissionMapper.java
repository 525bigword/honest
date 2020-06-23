package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPostPermissionMapper extends BaseMapper<SysPostPermission> {
    @Delete("delete from sys_post_permission where pid=#{pid}")
    void delSysPostPermissionByPid(@Param("pid") Integer pid);
    // TODO 查询所有权限中的列表权限
    @Select("select id,menu_name from sys_permission where required_permission=1")
    List<SysPermission> findSysPostByList();
    // TODO 按类型查询所有非列表权限
    @Select("select id,permission_name menu_name from sys_permission where menu_code= " +
            "(select menu_code from sys_permission where id=#{id} ) ")
    List<SysPermission> findSysPostUnList(@Param("id") Integer id);
    /**
     * 根据postid查询pid
     */
    @Select("select pid from sys_post_permission where postid=#{postid}")
    List<Integer> findSysPostPermissionByPostIdToPid(@Param("postid") Integer postid);
    /**
     * 按postid删除
     */
    @Delete("delete from sys_post_permission where postid=#{postid}")
    void delSysPostPermissionByPostId(@Param("postid")Integer postid);
    /**
     * 新增
     */
    @Insert("insert into sys_post_permission value(null,#{p.pid},#{postid},1,#{p.createTime},#{p.createId})")
    void addSysPostPermission(@Param("p")SysPost sysPost, @Param("postid")Integer postid);
    //根据postid查询
    @Select("select id,postid,pid from sys_post_permission where postid=#{postid} and stuas='1'")
    List<SysPostPermission> findSysPostPermissionByPostId(@Param("postid")Integer postId);
}
