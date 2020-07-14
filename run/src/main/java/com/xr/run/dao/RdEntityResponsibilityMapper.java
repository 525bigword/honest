package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Processrick;
import com.xr.run.entity.RdEntityResponsibility;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdEntityResponsibilityMapper extends BaseMapper<RdEntityResponsibility> {
    @Select("select id,title,content,create_time,create_id,staus from rd_entity_responsibility" +
            " where staus <> -1 and staus = CONCAT(#{staus})  and  title like CONCAT('%',#{title},'%') order by id desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdEntityResponsibility> findRdEntityResponsibilityIndex(Page page, @Param("title") String title,@Param("staus") Integer staus);

    List<RdEntityResponsibility> findRdEntityResponsibilityAll(@Param("title") String title);

    @Select("SELECT count(id) FROM (SELECT id,create_id FROM rd_entity_responsibility WHERE staus <> 0 and staus <> 1 ) rd_entity_responsibility WHERE create_id = #{sid}")
    Integer findrdRdEntityResponsibilityByWstatusToCount(@Param("sid") Integer sid) ;
}
