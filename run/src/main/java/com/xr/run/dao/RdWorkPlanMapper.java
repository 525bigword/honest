package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.RdWorkPlan;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdWorkPlanMapper extends BaseMapper<RdWorkPlan> {
    @Select("select rdid,title,content,create_time,create_id,create_name,staus from rd_work_plan where staus<>-1 and  title like CONCAT('%',#{title},'%') order by rdid desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdWorkPlan> findRdWorkPlanIndex(Page page, @Param("title") String title);

    @Select("select * from rd_work_plan order by create_time desc ")
    List<RdWorkPlan> findRdWorkPlanAll();
}
