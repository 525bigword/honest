package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.constants.Constants;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：wzh
 * @date ：Created in 2020/6/13 11:28
 * @Message :
 */
@RestController
@RequestMapping("SysStaff")
public class SysStaffController {
    @Autowired
    private SysStaffService  sysStaffService;



    @GetMapping("get/{sid}")
    @RequiresPermissions("staff:delete")
    public JSONObject getSysStaffBySid(@PathVariable Integer sid){
        SysStaff sysStaffBySid = sysStaffService.findSysStaffBySid(sid);
        return CommonUtil.successJson(sysStaffBySid);
    }
    @PostMapping("delete")
    @RequiresPermissions("staff:delete")
    public JSONObject delSysStaff(String str){
        System.out.println(str);
        Integer integer = sysStaffService.delSysStaff(str);
        return CommonUtil.successJson(integer);
    }
    @PostMapping("add")
    @RequiresPermissions("staff:add")
    public JSONObject addSysStaff(@RequestBody SysStaff sysStaff){
        System.out.println(sysStaff);
        Integer integer = sysStaffService.addSysStaff(sysStaff);
        return CommonUtil.successJson(integer);
    }

    @GetMapping("/get")
    public JSONObject findstatffs(){
        List<SysStaff> sysStaffAll = sysStaffService.findSysStaffAll();
        return CommonUtil.successJson(sysStaffAll);
        //return sysStaffAll;
    }

    @GetMapping("/get/{pageNum}/{pageRow}/{staus}")
    @RequiresPermissions("staff:list")
    public JSONObject findstatffs(@PathVariable Integer pageNum,@PathVariable Integer pageRow,String name,@PathVariable Integer staus, Integer mid){
        System.out.println(name+staus);
        System.out.println(mid+"``````````````````````````````````````````````````````````````````````````````````````");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SysStaff> page=new Page(pageNum,pageRow);
        IPage<SysStaff> sysStaffAll = sysStaffService.findSysStaffAll(page,name,staus,mid);
        return CommonUtil.successJson(sysStaffAll);
    }
    /**
     * 登录
     */
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");
        JSONObject jsonObject = sysStaffService.authLogin(requestJson);


        return jsonObject;
    }
    /**
     * 登出
     */
    @PostMapping("/logout")
    public JSONObject logout() {
        return sysStaffService.logout();
    }
    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getInfo")
    public JSONObject getInfo() {
        return sysStaffService.getInfo();
    }
    @PutMapping("update")
    @RequiresPermissions("staff:update")
    public JSONObject updateSysStaff(@RequestBody SysStaff sysStaff){
        System.out.println(sysStaff);
        sysStaffService.upSysStaff(sysStaff);
        return CommonUtil.successJson(1);
    }

}
