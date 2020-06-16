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
}
