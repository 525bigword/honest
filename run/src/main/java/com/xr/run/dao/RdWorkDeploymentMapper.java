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
    @Select("select rd.id,rd.title,rd.content,rd.create_time,rd.create_id,rd.staus,ss.name  from rd_work_deployment rd inner join sys_staff ss on rd.create_id = ss.sid where rd.staus <> -1 " +
            " and rd.staus = CONCAT(#{staus}) and  rd.title like CONCAT('%',#{title},'%') order by rd.id desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdWorkDeployment> findRdWorkDeploymentIndex(Page page, @Param("title") String title,@Param("staus") Integer staus);

    List<RdWorkDeployment> findRdWorkDeploymentAll(@Param("title") String title);


    @Select("select rd.id,rd.title,rd.content,rd.create_time,rd.create_id,rd.staus,ss.name  from rd_work_deployment rd inner join sys_staff ss on rd.create_id = ss.sid " +
            " where rd.staus <> -1 and rd.id= #{id}  order by rd.id desc ")
    RdWorkDeployment findRdWorkDeployementById(@Param("id") Integer id);
    @Select("SELECT count(id) FROM ( SELECT id,create_id FROM rd_work_deployment WHERE staus <> 2 and staus <> -1 ) rd_work_deployment WHERE create_id = #{sid}")
    Integer findrdRdWorkDeploymentByWstatusToCount(@Param("sid") Integer sid);

}
