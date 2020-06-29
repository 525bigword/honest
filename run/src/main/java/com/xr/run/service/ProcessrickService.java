package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Processrick;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface ProcessrickService extends IService<Processrick> {
    IPage<Processrick> findProcessrickIndex(Page page, String proName);
}
