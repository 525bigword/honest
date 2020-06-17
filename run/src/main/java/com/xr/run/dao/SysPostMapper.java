package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPost;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPostMapper extends BaseMapper<SysPost> {
    @Select("select pname from sys_post where pid = #{pid}")
    String findSysPostPnameByPid(@Param("pid")Integer pid);
    /**
     * 根据mid查询数量
     */
    @Select("select count(pid) from sys_post where mid=#{mid} and staus='1'")
    Integer findSysPostByMidTocount(@Param("mid")Integer mid);
}
