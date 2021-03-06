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
    @Select("select did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status` from spv_duty where dutyTitle like CONCAT('%',#{dutyTitle},'%') and `status`<>-1 order by did desc")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvDuty> findSpvDuty(Page page, String dutyTitle);

    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent},`status`=#{status} where did=#{did}")
    void updateSpvDutyByDid(SpvDuty spvDuty);

    @Update("update spv_duty set dutyType=#{dutyType},dutyTitle=#{dutyTitle},bid=#{bid},dutyContent=#{dutyContent},dutyAccessory=#{dutyAccessory},dutyAccessoryName=#{dutyAccessoryName},`status`=#{status} where did=#{did}")
    void updateSpvDutyFileByDid(SpvDuty spvDuty);

    @Insert("insert into spv_duty(did,dnumId,dutyType,dutyTitle,dutyContent,bid,dutyAccessory,dutyAccessoryName,newTime,dCreateId,`status`) VALUES(null,#{dnumId},#{dutyType},#{dutyTitle},#{dutyContent},#{bid},#{dutyAccessory},#{dutyAccessoryName},NOW(),#{dCreateId},#{status})")
    void insertSpvDuty(SpvDuty spvDuty);

    @Update("update spv_duty set `status`=-1 where did=#{did}")
    void deleteSpvDutyByDid(int did);

    @Select("select DutyAccessory from spv_duty where did=#{did}")
    public String findSpvDutyByFile(int did);

    @Select("select mid,mechanism_name from sys_mechanism where staus='正常' and parent=#{parent}")
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

    @Select("select * from spv_duty ORDER BY NewTime desc limit 0,5")
    List<SpvDuty> findSpvDutyTopFive();

}
