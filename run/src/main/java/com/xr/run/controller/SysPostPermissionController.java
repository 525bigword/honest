package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPpost;
import com.xr.run.service.SysPostPermissionService;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("SysPostPermission")
public class SysPostPermissionController {
    @Autowired
    private SysPostPermissionService sysPostPermissionService;
    @Autowired
    private SysStaffService sysStaffService;
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
    @PostMapping("get/{pageNum}/{pageRow}")
//    @RequiresPermissions("ppost:list")
    public JSONObject getPage(@PathVariable Integer pageNum, @PathVariable Integer pageRow,  @RequestBody JSONObject jsonObject){
        System.out.println(pageNum);
        System.out.println(pageRow);
        System.out.println(jsonObject.get("message"));
        System.out.println(jsonObject.get("pname"));
        System.out.println(jsonObject.get("mids"));
        pageNum=pageNum<0||null==pageNum?0:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page page=new Page(pageNum,pageRow);
        List<SysPpost> sysPostPage = sysPostPermissionService.findSysPostPage(pageNum,pageRow, jsonObject);
        Integer count = sysPostPermissionService.findSysPostPage(jsonObject);
        Map map=new HashMap();
        map.put("count",count);
        map.put("data",sysPostPage);
        return CommonUtil.successJson(map);
    }
    @PostMapping("add")
    @RequiresPermissions("ppost:add")
    public JSONObject add(@RequestBody SysPpost sysPpost){
        System.out.println(sysPpost);
        sysPpost.setCreateTime(DateUtil.getDate());
        sysPostPermissionService.add(sysPpost);
        return CommonUtil.successJson();
    }
    @PostMapping("update")
    @RequiresPermissions("ppost:update")
    public JSONObject upSysPost(@RequestBody SysPpost sysPpost){
        System.out.println(sysPpost);
        sysPostPermissionService.upSysPost(sysPpost);
        return CommonUtil.successJson(1);
    }
    @PostMapping("del")
    @RequiresPermissions("ppost:delete")
    public JSONObject delSysPost(String arr){
        String[] split = arr.split(",");
        for (int i=0;i<split.length;i++){
            System.out.println(split[i]);
        }
        Integer sysStaffToCount = sysStaffService.findSysStaffToCount(split);
        if(sysStaffToCount>0){
            return CommonUtil.successJson(0);
        }else{
            sysPostPermissionService.delSysPost(split);
            return CommonUtil.successJson(1);
        }
    }
    @GetMapping("getPpost/{mid}")
    public JSONObject getSysPpost(@PathVariable Integer mid){
        List<SysPpost> sysPpostByMid = sysPostPermissionService.findSysPpostByMid(mid);
        return CommonUtil.successJson(sysPpostByMid);
    }
}
