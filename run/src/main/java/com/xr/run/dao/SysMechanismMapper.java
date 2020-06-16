package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysMechanism;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMechanismMapper extends BaseMapper<SysMechanism> {
    @Select("select mechanism_name from sys_mechanism where mid=#{mid}")
    String findMechanismNameByMid(@Param("mid") Integer mid);

    @Select("select mid,mechanism_name,sid,parent,branch,create_time,create_id,staus from sys_mechanism where mechanism_name like CONCAT('%',#{mechanism},'%') and sid in ${ids} and staus=#{staus} limit #{index},#{pagenum}")
    @Results({
            @Result(property = "sname",column = "sid",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "parentName",column = "parent",one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(property = "branchName",column = "branch",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "createName",column = "create_id",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
    })
    List<SysMechanism> findSysMechanismPage(@Param("index") Integer index, @Param("pagenum") Integer pagenum, @Param("mechanism") String mechanism, @Param("ids") String ids, @Param("staus") String staus);
    @Select("select count(mid) from sys_mechanism where mechanism_name like CONCAT('%',#{mechanism},'%') and sid in ${ids} and staus=#{staus}")
    Integer findSysMechanismPageCount(@Param("mechanism") String mechanism, @Param("ids") String ids, @Param("staus") String staus);

    /**
     * 根据Id查询机构名称
     */
    @Select("select mechanism_name from sys_mechanism where mid=#{mid}")
    String findSysMechanismByIdToMechanismName(@Param("mid")Integer mid);
    /**
     * 按parent查
     */
    @Select("select mid,mechanism_name,sid,parent,branch,create_time,create_id,staus from sys_mechanism where parent=#{parent} and staus='正常'")
    @Results({
            @Result(column = "mechanismName",property = "lable"),
            @Result(column = "mid",property = "value"),
            @Result(column = "mid",property = "mid")
    })
    List<SysMechanism> findSysMechanismByParentAll(@Param("parent")Integer parent);

}
