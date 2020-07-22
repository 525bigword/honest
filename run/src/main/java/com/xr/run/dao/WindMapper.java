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

    @Select("select w.wid,w.wTitle,w.wContent,w.wContributor,w.wCreateTime,w.wCreateId,w.wStatus,ss.name as cname from Wind w inner join sys_staff ss on w.wCreateId=ss.sid where w.wStatus =3 and  w.wTitle like CONCAT('%',#{wTitle},'%') order by w.wid desc")
    @Results({
            @Result(column = "wContributor",property = "wnew",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "wCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Wind> findWind1(Page page,@Param("wTitle") String wTitle);

    @Update("update Wind set wTitle=#{wTitle},wContent=#{wContent},wContributor=#{wContributor},wStatus=#{wStatus} where wid=#{wid}")
    void updateWindByWid(Wind wind);

    @Insert("insert into Wind(wid,wTitle,wContent,wContributor,wCreateTime,wCreateId,wStatus) VALUES(null,#{wTitle},#{wContent},#{wContributor},date_add(NOW(),INTERVAL -8 hour),#{wCreateId},#{wStatus})")
    @Options(useGeneratedKeys = true,keyProperty = "wid")
    void insertWind(Wind wind);

    @Delete("delete from  Wind where wid=#{wid}")
    void deleteWindByWid(int wid);

    @Select("SELECT count(wid) FROM (SELECT wid,wCreateId FROM wind WHERE wStatus <> 3 and wStatus <> -1) wind WHERE wCreateId = #{sid}")
    Integer findWindByWstatusToCount(@Param("sid")Integer sid);

    @Select("select ww.wid,ww.wTitle,ww.wContent,ww.wContributor,ww.wCreateTime,ww.wCreateId,ww.wStatus,ss.name as cname from " +
            " Wind ww inner join sys_staff ss on ww.wCreateId=" +
            " ss.sid  where ww.wid=#{wid}")
    @Results({
            @Result(column = "wContributor",property = "wnew",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "wCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    Wind findWindByWid(int wid);
}
