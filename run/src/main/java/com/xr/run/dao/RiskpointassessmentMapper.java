package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Riskevaluate;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.entity.SysMechanism;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.InputMismatchException;
import java.util.List;

@Repository
public interface RiskpointassessmentMapper extends BaseMapper<Riskpointassessment> {
    @Select("select mid from sys_mechanism  where mid not in (\n" +
            "select riskImfomation from riskpointassessment)")
    public List<SysMechanism> notUpdatedDept(Riskpointassessment riskpointassessment);

    @Insert("insert into riskpointassessment value(0,#{riskImfomation},#{numberOneRisks}," +
            "#{numberTwoRisks},#{numberThreeRisks},NOW(),#{riskCreateId},#{riskCreateName},1)")
    public void add(Riskpointassessment riskpointassessment);

    @Update("update riskpointassessment set numberOneRisks=#{numberOneRisks},numberTwoRisks=#{numberTwoRisks}," +
            "numberThreeRisks=#{numberThreeRisks} where riskImfomation=#{riskImfomation}")
    public void update(Riskpointassessment riskpointassessment);

    @Select("select numberOneRisks,numberTwoRisks,numberThreeRisks " +
            "from riskpointassessment where riskImfomation=#{riskImfomation}")
    Riskpointassessment findBy(@Param("riskImfomation")Integer riskImfomation);


/*
    @Insert("insert into riskpointassessment value(0,#{riskimfomation},#{numberonerisks}," +
            "#{numbertworisks},#{numberthreerisks},NOW(),#{riskcreateid},#{riskcreatename},1)")
    public void add(Riskpointassessment riskPointAssessment);

    @Update("update riskpointassessment set numberOneRisks=#{numberonerisks},numberTwoRisks=#{numbertworisks}," +
            "numberThreeRisks=#{numberthreerisks} where riskImfomation=#{riskimfomation}\n")
    public void update(Riskpointassessment riskPointAssessment);
*/





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
    @Select("select numberOneRisks,numberTwoRisks,numberThreeRisks from riskpointassessment ")
    List<Riskpointassessment> getList();

    /**
     * 根据部门ID查询
     *
     * @param riskImfomation
     * @return
     */
    @Select("select numberOneRisks,numberTwoRisks,numberThreeRisks from riskpointassessment where riskImfomation=#{riskImfomation} ")
    Riskpointassessment getOneByRiskImfomation(Integer riskImfomation);


    void updateByRiskImfomation1(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);


    void updateByRiskImfomation2(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);


    void updateByRiskImfomation3(@Param("riskImfomation") Integer riskImfomation, @Param("num") Integer num);
}
