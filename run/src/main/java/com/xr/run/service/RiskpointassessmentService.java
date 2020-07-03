package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Riskpointassessment;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface RiskpointassessmentService extends IService<Riskpointassessment> {

    /**
     * 同步风险点评表
     */
    void syncRiskpointassessment();

    /**
     * 新增
     *
     * @param riskpointassessment
     */
    void addRiskpointassessment(Riskpointassessment riskpointassessment);

    /**
     * 获取列表
     *
     * @return
     */
    List<Riskpointassessment> getList();

    /**
     * 根据部门ID查询
     *
     * @param riskImfomation
     * @return
     */
    Riskpointassessment getOneByRiskImfomation(Integer riskImfomation);

    /**
     * 更新
     * @param riskImfomation
     * @param pGrade
     */
    void updateByRiskImfomation(Integer riskImfomation, String pGrade, Integer num);
}
