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
    @Select("SELECT did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus,dPdf,dVideo,dVideoName from datacollection where dTitle like CONCAT('%',#{dTitle},'%') order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
            one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Datacollection> findDataConllection(Page page, String dTitle);

    @Update("update datacollection set dTitle=#{dTitle},dFileName=#{dFileName},dFile=#{dFile},dStatus=#{dStatus},dPdf=#{dPdf} where did=#{did}")
    void updateDataConllectionByDid(Datacollection datacollection);

    @Update("update datacollection set dTitle=#{dTitle},dStatus=#{dStatus} where did=#{did}")
    void updateDataConllectionByFile(Datacollection datacollection);

    @Update("update datacollection set dTitle=#{dTitle},dVideo=#{dVideo},dVideoName=#{dVideoName},dStatus=#{dStatus} where did=#{did}")
    void updateDataConllectionByVideo(Datacollection datacollection);

    @Update("update datacollection set dTitle=#{dTitle},dVideo=#{dVideo},dVideoName=#{dVideoName},dFileName=#{dFileName},dFile=#{dFile},dStatus=#{dStatus},dPdf=#{dPdf} where did=#{did}")
    void updateDataConllectionByVideoAndFile(Datacollection datacollection);

    @Insert("insert into datacollection(did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus,dPdf,dVideo,dVideoName) VALUES(null,#{dTitle},#{dFileName},#{dFile},NOW(),#{dCreateId},#{dStatus},#{dPdf},#{dVideo},#{dVideoName})")
    void insertDataConllection(Datacollection datacollection);

    @Delete("delete from  datacollection where did=#{did}")
    void deleteDataConllectionByDid(int did);

    @Select("select dFile from datacollection where did=#{did}")
    String findDatacollectionByFile(int did);

    @Select("select dVideo from datacollection where did=#{did}")
    String findDatacollectionBydVideo(int did);
    //按创建者查询状态为创建、待审
    @Select("select count(did) from datacollection where dCreateId=#{sid} and dStatus=1 ")
    Integer findDatacollectionByStatusAndSidToCount(@Param("sid")Integer sid);
    //根据ID查询
    @Select("select did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus,dPdf,dVideo,dVideoName from datacollection where did=#{did}")
    Datacollection findDatacollectionById(@Param("did") int did);
}
