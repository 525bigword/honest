package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xr.run.entity.SysPermission;
import com.xr.run.service.SysPostPermissionService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("SysPostPermission")
public class SysPostPermissionController {
    @Autowired
    private SysPostPermissionService sysPostPermissionService;

    @GetMapping("get")
    public JSONObject get(){
        List<SysPermission> treeSysPost = sysPostPermissionService.findTreeSysPost();
        return CommonUtil.successJson(treeSysPost);
    }
    @GetMapping("get/{pid}")
    public JSONObject gets(@PathVariable Integer pid){
        List<Integer> sysPostPermissionByPostIdToPid = sysPostPermissionService.findSysPostPermissionByPostIdToPid(pid);
        return CommonUtil.successJson(sysPostPermissionByPostIdToPid);
    }
}
