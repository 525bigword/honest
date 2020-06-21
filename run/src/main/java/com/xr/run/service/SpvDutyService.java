package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SpvDuty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface SpvDutyService extends IService<SpvDuty> {
    /**
     * 查询所有的责任监督+分页+dutytitle模糊查询
     * @param page
     * @param dutyTitle
     * @return
     */
    IPage<SpvDuty> findSpvDuty(Page page, String dutyTitle);

    /**
     * 修改责任监督
     * @param spvDuty
     */
    void updateSpvDutyByDid(SpvDuty spvDuty);

    /**
     * 修改责任监督 并修改文件名和路径
     * @param spvDuty
     */
    void updateSpvDutyFileByDid(SpvDuty spvDuty);

    /**
     * 新增责任监督
     * @param spvDuty
     */
    void insertSpvDuty(SpvDuty spvDuty);

    /**
     * 删除责任监督
     * @param did
     */
    void deleteSpvDutyByDid(int did);

    /**
     * 查询单个文件的路径
     * @param did
     * @return
     */
    public String findSpvDutyByFile(int did);
}
