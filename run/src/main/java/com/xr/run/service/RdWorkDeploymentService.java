package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.RdWorkDeployment;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface RdWorkDeploymentService extends IService<RdWorkDeployment> {
    IPage<RdWorkDeployment> findRdWorkDeploymentIndex(Page page, String title,Integer staus);

    RdWorkDeployment findRdWorkDeployementById(Integer id);

    List<RdWorkDeployment> findRdWorkDeploymentAll(String title);
}
