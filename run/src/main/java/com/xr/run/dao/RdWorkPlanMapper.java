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
    @Select("select rdid,title,content,create_time,create_id,create_name,staus from " +
            " rd_work_plan where staus <> -1 and staus = CONCAT(#{staus}) and  title like CONCAT('%',#{title},'%') order by rdid desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdWorkPlan> findRdWorkPlanIndex(Page page, @Param("title") String title,@Param("staus") Integer staus);



    List<RdWorkPlan> findRdWorkPlanAll(@Param("title") String title);

    @Select("SELECT count(rdid) FROM ( SELECT rdid,create_id FROM rd_work_plan WHERE staus <> 0 and staus <> 1 ) rd_work_plan WHERE create_id = #{sid}")
    Integer findrdWorkPlanByWstatusToCount(@Param("sid") Integer sid);
}
