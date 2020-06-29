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


public interface PostriskcombingService extends IService<Postriskcombing> {
    IPage<Postriskcombing> findPostriskCombingIndex(Page page, String pRiskPointDescription);
}
