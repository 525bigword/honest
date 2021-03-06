package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Wind;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface WindMapper extends BaseMapper<Wind> {
    @Select("select wid,wTitle,wContent,wContributor,wCreateTime,wCreateId,wStatus from Wind where wTitle like CONCAT('%',#{wTitle},'%') order by wid desc")
    @Results({
            @Result(column = "wContributor",property = "wnew",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "wCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Wind> findWind(Page page,@Param("wTitle") String wTitle);

    @Update("update Wind set wTitle=#{wTitle},wContent=#{wContent},wContributor=#{wContributor},wStatus=#{wStatus} where wid=#{wid}")
    void updateWindByWid(Wind wind);

    @Insert("insert into Wind(wid,wTitle,wContent,wContributor,wCreateTime,wCreateId,wStatus) VALUES(null,#{wTitle},#{wContent},#{wContributor},NOW(),#{wCreateId},#{wStatus})")
    void insertWind(Wind wind);

    @Delete("delete from  Wind where wid=#{wid}")
    void deleteWindByWid(int wid);

    @Select("select count(wid) from wind where wCreateId=#{sid} and wStatus=1")
    Integer findWindByWstatusToCount(@Param("sid")Integer sid);
}
