package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.RdHonestConversation;
import com.xr.run.entity.RdWorkDeployment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdWorkDeploymentMapper extends BaseMapper<RdWorkDeployment> {
    @Select("select id,title,content,create_time,create_id,staus from rd_work_deployment where staus<>-1 and  title like CONCAT('%',#{title},'%') order by id desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdWorkDeployment> findRdWorkDeploymentIndex(@Param("page") Page page, @Param("title") String title);

    @Select("select * from rd_work_deployment order by create_time desc")
    List<RdWorkDeployment> findRdWorkDeploymentAll();
}
