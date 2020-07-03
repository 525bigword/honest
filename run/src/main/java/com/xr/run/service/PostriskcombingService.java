package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Postriskcombing;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface PostriskcombingService extends IService<Postriskcombing> {
    IPage<Postriskcombing> findPostriskCombingIndex(Page page, String pRiskPointDescription);

    /**
     * 查询列表
     *
     * @return
     */
    List<Postriskcombing> getList();


    /**
     * 根据ID删除
     *
     * @param pid
     */
    void deleteById(Integer pid);

    /**
     * 更新信息
     *
     * @param postriskcombing
     */
    void updateByPid(Postriskcombing postriskcombing);


    /**
     * 新增
     *
     * @param postriskcombing
     */
    void addPostriskcombing(Postriskcombing postriskcombing);

    /**
     * 根据风险项目名称查询列表
     *
     * @param pProject
     * @return
     */
    List<Postriskcombing> getListByPproject(String pProject);


    /**
     * 多条件查询列表
     *
     * @param pInfomationId
     * @param pDeptId
     * @return
     */
    List<Postriskcombing> getListBy(Integer pInfomationId, Integer pDeptId, String pGrade);


    /**
     * 根据部门ID和风险等级查询
     *
     * @param pDeptId
     * @param pGrade
     * @return
     */
    List<Postriskcombing> findByPDeptIdAndPGrade(Integer pDeptId, String pGrade);
}
