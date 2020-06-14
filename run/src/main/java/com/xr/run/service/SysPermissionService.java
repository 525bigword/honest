package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysPermission;
import lombok.Data;


public interface SysPermissionService extends IService<SysPermission> {
    JSONObject getUserPermission(String username);
}
