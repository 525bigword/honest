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
    @Select("select rd.id,rd.title,rd.content,rd.create_time,rd.create_id,rd.staus,ss.name from rd_entity_responsibility rd" +
            " inner join sys_staff ss on rd.create_id= ss.sid  where rd.staus <> -1 and rd.staus = CONCAT(#{staus})  and  rd.title like CONCAT('%',#{title},'%') order by rd.id desc")
    @Results({
            @Result(column = "create_id",property = "createId"),
            @Result(column = "create_id",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<RdEntityResponsibility> findRdEntityResponsibilityIndex(Page page, @Param("title") String title,@Param("staus") Integer staus);

    List<RdEntityResponsibility> findRdEntityResponsibilityAll(@Param("title") String title);


    @Select("select rd.*,ss.name from  rd_entity_responsibility rd inner join sys_staff ss on rd.create_id= ss.sid " +
            " where rd.id =#{id}")
    RdEntityResponsibility findRdEntityResponsibilityById(@Param("id") Integer id);
}
