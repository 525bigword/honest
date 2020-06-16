package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysStaffMapper extends BaseMapper<SysStaff> {
    /**
     * 根据用户名和密码查询对应的用户
     */
    @Select("SELECT u.sid,u.name, u.sex,u.age,u.educational_background,u.political_appearance,u.phone,u.mid,u" +
            ".username,u.password,u.pid,u.create_time,u.create_id,u.staus FROM " +
            " sys_staff u WHERE u.username = #{username} AND u.password = #{password} AND u.staus = '1'")
    SysStaff getUser(@Param("username") String username, @Param("password") String password);
    @Select("select sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid," +
            "create_time,create_id,staus from sys_staff where name like CONCAT('%',#{name},'%') and staus=#{staus}")
    @Results({
            @Result(property = "mechanismname",column = "mid", one=@One(select = "com.xr.run.dao.SysMechanismMapper.findMechanismNameByMid",fetchType = FetchType.DEFAULT)),
            @Result(property = "postname",column = "pid", one=@One(select = "com.xr.run.dao.SysPostMapper.findSysPostPnameByPid",fetchType = FetchType.DEFAULT)),
    })
    IPage<SysStaff> findSysStaffAll(Page page,@Param("name") String name, @Param("staus")Integer staus);
    /**
     * 按名字模糊查询员工id
     */
    @Select("select sid from sys_staff where name like CONCAT('%',#{name},'%')")
    List<Integer> findSysStaffByNameToId(@Param("name") String name);
    /**
     * 按id查询员工名字
     */
    @Select("select name from sys_staff where sid=#{sid}")
    String findSysStaffByIdToName(@Param("sid")Integer sid);

    /**
     * 查询所有员工
     * @return
     */
    @Select("select sid,name from sys_staff")
    List<SysStaff> findSysStaffAll();
}
