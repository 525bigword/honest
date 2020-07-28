package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SpvDuty;
import com.xr.run.entity.SysMechanism;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpvDutyMapper extends BaseMapper<SpvDuty> {
    @Select("select did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutypdf,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status`,tongbao from spv_duty where dutyTitle like CONCAT('%',#{dutyTitle},'%') and `status`<>-1 order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvDuty> findSpvDuty(Page page, @Param("dutyTitle") String dutyTitle);

    @Select("select did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status`,tongbao from spv_duty where dutyTitle like CONCAT('%',#{dutyTitle},'%') and `status` =0 or `status` =6  order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvDuty> findSpvDuty1(Page page, @Param("dutyTitle") String dutyTitle);


    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent},`status`=#{status} where did=#{did}")
    void updateSpvDutyByDid(SpvDuty spvDuty);

    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent},dutyAccessory=#{dutyAccessory},dutyAccessoryName=#{dutyAccessoryName},`status`=#{status},dutypdf=#{dutypdf} where did=#{did}")
    void updateSpvDutyFileByDid(SpvDuty spvDuty);

    @Insert("insert into spv_duty(did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status`,dutypdf) VALUES(null,#{dnumId},#{dutyType},#{dutyTitle},#{dutyContent},#{bid},#{dutyAccessory},#{dutyAccessoryName},date_add(NOW(),INTERVAL -8 hour),#{dCreateId},#{status},#{dutypdf})")
    void insertSpvDuty(SpvDuty spvDuty);

    @Update("update spv_duty set `status`=-1 where did=#{did}")
    void deleteSpvDutyByDid(int did);

    @Select("select DutyAccessory from spv_duty where did=#{did}")
    public String findSpvDutyByFile(int did);

    @Select("select dutypdf from spv_duty where did=#{did}")
    String findSpvDutyByPdf(int did);

    @Select("select mid,mechanism_name from sys_mechanism where staus='正常' and parent=#{parent} order by sort")
    @Results({
            @Result(column = "mid",property = "mid"),
            @Result(column = "mechanism_name",property = "mechanismName"),
            @Result(column = "mid",property = "chilrenMechanism",
            many = @Many(select = "com.xr.run.dao.SpvDutyMapper.findDid",fetchType = FetchType.DEFAULT))
    })
    List<SysMechanism> findDid(int parent);

    @Update("update spv_duty set `status`=#{status} where did=#{did}")
    void updateStatusByDid(SpvDuty spvDuty);

    @Select("select dutyContent from spv_duty where did=#{did}")
    String getSpvDuty(int did);

    @Select("select tongbao from spv_duty where did=#{did}")
    String getTongzhi(int did);

    @Update("update spv_duty set tongbao=#{tongbao} where did=#{did}")
    void updateTongBaoByDid(SpvDuty spvDuty);

    @Select("select sd.did,sd.dnumId,sd.dutyType,sd.dutyTitle,sd.dutyContent,sd.bid,sd.dutyAccessory," +
            " sd.dutyAccessoryName,sd.newTime,sd.dCreateId,sd.`status`,ss.name from spv_duty sd inner join sys_staff ss on sd.dCreateId = ss.sid" +
            " where sd.did=#{did} and sd.`status`<>-1 ")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    SpvDuty findSpvDutyByDid(int did);

    @Select("select sd.*,ss.name from spv_duty sd inner join sys_staff ss on sd.dCreateId = ss.sid where sd.status = 0 or sd.status = 6 ORDER BY sd.NewTime desc limit 0,5")
    List<SpvDuty> findSpvDutyTopFive();
    @Select("SELECT count(did) FROM (SELECT did,dCreateId FROM spv_duty WHERE `status` <> 0 and `status` <> -1 ) spv_duty WHERE dCreateId = #{sid}")
    Integer findSpvDutyByWstatusToCount(@Param("sid")Integer sid);

}
