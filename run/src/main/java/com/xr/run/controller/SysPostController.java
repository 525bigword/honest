package com.xr.run.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPostService;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("syspost")
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysStaffService sysStaffService;
    @PostMapping("get/{pageNum}/{pageRow}")
    @RequiresPermissions("post:list")
    public JSONObject getPage(@PathVariable Integer pageNum, @PathVariable Integer pageRow,  @RequestBody JSONObject jsonObject){
        System.out.println(pageNum);
        System.out.println(pageRow);
        System.out.println(jsonObject.get("message"));
        System.out.println(jsonObject.get("pname"));
        System.out.println(jsonObject.get("mids"));
        pageNum=pageNum<0||null==pageNum?0:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page page=new Page(pageNum,pageRow);
        List<SysPost> sysPostPage = sysPostService.findSysPostPage(pageNum,pageRow, jsonObject);
        Integer count = sysPostService.findSysPostPage(jsonObject);
        Map map=new HashMap();
        map.put("count",count);
        map.put("data",sysPostPage);
        return CommonUtil.successJson(map);
    }

    @PostMapping("add")
    @RequiresPermissions("post:add")
    public JSONObject addSysPost(@RequestBody SysPost sysPost){
        System.out.println(sysPost);
        try {
            sysPostService.addSysPost(sysPost);
            return CommonUtil.successJson(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonUtil.successJson(0);
    }

    @PostMapping("update")
    @RequiresPermissions("post:update")
    public JSONObject upSysPost(@RequestBody SysPost sysPost){
        System.out.println(sysPost);
        sysPostService.upSysPost(sysPost);
        return CommonUtil.successJson(1);
    }

    public JSONObject getAll(){
        return null;
    }
    @PostMapping("del")
    @RequiresPermissions("post:delete")
    public JSONObject delSysPost(String arr){
        String[] split = arr.split(",");
        for (int i=0;i<split.length;i++){
            System.out.println(split[i]);
        }
        Integer sysStaffToCount = sysStaffService.findSysStaffToCount(split);
        if(sysStaffToCount>0){
            return CommonUtil.successJson(0);
        }else{
            sysPostService.delSysPost(split);
            return CommonUtil.successJson(1);
        }
    }
    @GetMapping("get")
    public JSONObject getAllSysPost(){

        List<SysPost> sysPost = sysPostService.getSysPost();
        return CommonUtil.successJson(sysPost);
    }
}
