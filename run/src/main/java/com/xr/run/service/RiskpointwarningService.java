package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Riskpointwarning;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public  interface RiskpointwarningService extends IService<Riskpointwarning> {
    /**
     * 查找全部风险点预警并分页
     * @param page
     * @param wTitle
     * @return
     */
    IPage<Riskpointwarning> findRiskpointwarning(Page page, String wTitle);

    /**
     * 修改风险点预警
     * @param riskpointwarning
     */
    void updateRiskpointwarningByWid(Riskpointwarning riskpointwarning);

    /**
     * 新增风险点预警
     * @param riskpointwarning
     */
    void insertRiskpointwarning(Riskpointwarning riskpointwarning);

    /**
     * 删除风险点预警
     * @param wid
     */
    void deleteRiskpointwarningByWid(int wid);
}
