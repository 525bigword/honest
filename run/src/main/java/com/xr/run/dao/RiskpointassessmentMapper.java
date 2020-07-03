package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Riskevaluate;
import com.xr.run.entity.Riskpointassessment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskpointassessmentMapper extends BaseMapper<Riskpointassessment> {
    /**
     * 新增
     *
     * @param riskpointassessment
     */
    @Insert("insert into riskpointassessment(riskImfomation,numberOneRisks,numberTwoRisks,numberThreeRisks,riskCreateTime,riskCreateId,riskCreateName,riskStatus) values(#{riskImfomation},#{numberOneRisks},#{numberTwoRisks},#{numberThreeRisks},#{riskCreateTime},#{riskCreateId},#{riskCreateName},#{riskStatus})")
    void addRiskpointassessment(Riskpointassessment riskpointassessment);


    /**
     * 获取列表
     *
     * @return
     */
    @Select("select * from riskpointassessment where riskStatus=1")
    List<Riskpointassessment> getList();

    /**
     * 根据部门ID查询
     *
     * @param riskImfomation
     * @return
     */
    @Select("select * from riskpointassessment where riskImfomation=#{riskImfomation} and  riskStatus=1")
    Riskpointassessment getOneByRiskImfomation(Integer riskImfomation);


    void updateByRiskImfomation1(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);


    void updateByRiskImfomation2(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);


    void updateByRiskImfomation3(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);
}
