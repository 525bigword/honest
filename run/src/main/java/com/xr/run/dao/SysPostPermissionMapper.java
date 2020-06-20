package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
