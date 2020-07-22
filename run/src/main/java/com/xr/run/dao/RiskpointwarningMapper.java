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
    @Select("select wid,numId,wTitle,wContent,wCreateTime,wCreateId,wStatus from riskpointwarning where wStatus=0 and wTitle like CONCAT('%',#{wTitle},'%') and wContent like CONCAT('%',#{wContent},'%') order by wid desc")
    @Results({
            @Result(column = "wCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Riskpointwarning> findRiskpointwarning(Page page, String wTitle,String wContent);

    @Update("update riskpointwarning set wTitle=#{wTitle},wContent=#{wContent} where wid=#{wid}")
    void updateRiskpointwarningByWid(Riskpointwarning riskpointwarning);

    @Insert("insert into riskpointwarning(wid,numId,wTitle,wContent,wCreateTime,wCreateId,wStatus) VALUES(null,#{numId},#{wTitle},#{wContent},date_add(NOW(),INTERVAL -8 hour),#{wCreateId},0)")
    void insertRiskpointwarning(Riskpointwarning riskpointwarning);

    @Delete("update riskpointwarning set wStatus=1 where wid=#{wid}")
    void deleteRiskpointwarningByWid(int wid);
}
