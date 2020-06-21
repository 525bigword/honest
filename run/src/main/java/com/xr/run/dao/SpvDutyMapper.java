package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SpvDuty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface SpvDutyMapper extends BaseMapper<SpvDuty> {
    @Select("select did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status` from spv_duty where dutyTitle like CONCAT('%',#{dutyTitle},'%') order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvDuty> findSpvDuty(Page page, String dutyTitle);

    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent} where did=#{did}")
    void updateSpvDutyByDid(SpvDuty spvDuty);

    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent},dutyAccessory=#{dutyAccessory},dutyAccessoryName=#{dutyAccessoryName} where did=#{did}")
    void updateSpvDutyFileByDid(SpvDuty spvDuty);

    @Insert("insert into spv_duty(did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status`) VALUES(null,#{dnumId},#{dutyType},#{dutyTitle},#{dutyContent},#{bid},#{dutyAccessory},#{dutyAccessoryName},NOW(),#{dCreateId},#{status})")
    void insertSpvDuty(SpvDuty spvDuty);

    @Delete("delete from  spv_duty where did=#{did}")
    void deleteSpvDutyByDid(int did);

    @Select("")
    public String findSpvDutyByFile(int did);
}
