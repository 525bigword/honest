package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysPermission;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;


public interface SysPermissionService extends IService<SysPermission> {
    JSONObject getUserPermission(String username);
    Integer addSysPermission(SysPermission sysPermission) throws Exception;
    IPage<SysPermission> findSysPermissionPage(Page page,String name, Integer required) throws Exception;
    Integer delSysPermissionPage(SysPermission sysPermission);
    void updateSysPermission(SysPermission sysPermission);
}
