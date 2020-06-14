package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPostPermission;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface SysPostPermissionService extends IService<SysPostPermission> {

}
