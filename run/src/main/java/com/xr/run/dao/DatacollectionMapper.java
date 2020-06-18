package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface DatacollectionMapper extends BaseMapper<Datacollection> {
    @Select("SELECT did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus from datacollection where dTitle like CONCAT('%',#{dTitle},'%') order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
            one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Datacollection> findDataConllection(Page page, String dTitle);

    @Update("update datacollection set dTitle=#{dTitle},dFileName=#{dFileName},dFile=#{dFile},dStatus=#{dStatus} where did=#{did}")
    void updateDataConllectionByDid(Datacollection datacollection);

    @Insert("insert into datacollection(did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus) VALUES(null,#{dTitle},#{dFileName},#{dFile},NOW(),#{dCreateId},#{dStatus})")
    void insertDataConllection(Datacollection datacollection);

    @Delete("delete from  datacollection where did=#{did}")
    void deleteDataConllectionByDid(int did);

    @Select("select dFile from datacollection where did=#{did}")
    public String findDatacollectionByFile(int did);

}
