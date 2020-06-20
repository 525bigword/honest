package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.entity.Riskpointwarning;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public  interface RiskpointwarningMapper extends BaseMapper<Riskpointwarning> {
    @Select("select wid,wTitle,wContent,wCreateTime,wCreateId,wStatus from riskpointwarning where wTitle like CONCAT('%',#{wTitle},'%') order by wid desc")
    @Results({
            @Result(column = "wCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Riskpointwarning> findRiskpointwarning(Page page, String wTitle);

    @Update("update riskpointwarning set wTitle=#{wTitle},wContent=#{wContent},wStatus=#{wStatus} where wid=#{wid}")
    void updateRiskpointwarningByWid(Riskpointwarning riskpointwarning);

    @Insert("insert into riskpointwarning(wid,wTitle,wContent,wCreateTime,wLoginId,wStatus) VALUES(null,#{wTitle},#{wContent},NOW(),#{wCreateId},#{wStatus})")
    void insertRiskpointwarning(Riskpointwarning riskpointwarning);

    @Delete("delete from  riskpointwarning where wid=#{wid}")
    void deleteRiskpointwarningByWid(int wid);
}
