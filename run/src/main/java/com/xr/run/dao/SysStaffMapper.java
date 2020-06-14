package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
            "create_time,create_id,staus from sys_staff")
    IPage<SysStaff> findSysStaffAll(Page page);
}
