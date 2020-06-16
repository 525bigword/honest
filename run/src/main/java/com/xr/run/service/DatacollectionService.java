package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface DatacollectionService extends IService<Datacollection> {
    IPage<Datacollection> findDataConllection(Page page);
}
