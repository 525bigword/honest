package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysMechanism;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface SysMechanismService extends IService<SysMechanism> {
    Map<String,Object> findSysMechanismPage(Integer pageNum, Integer pageRow, String mechanism, String principal, String staus);
    List<SysMechanism> findSysMechanismAll();
    void addSysMechanism(SysMechanism sysMechanism);
    Integer delSysMechanism(Integer mid);
    void unDelSysMechanism(Integer mid);
    void upSysmechanism(SysMechanism sysMechanism);
    List<SysMechanism> findAllSysMechanism();
    /**
     * 查询所有的部门
     */
    public List<SysMechanism> findSysMechanisms();

    /**
     * 根据parent查询旗下部门
     */
    List<SysMechanism> findSysMechanismByParentAll(Integer parent);

    SysMechanism findByMid(int mid);

    List<SysMechanism> getAll();
}
