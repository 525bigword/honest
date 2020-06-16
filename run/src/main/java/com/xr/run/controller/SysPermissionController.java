package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysPermission;
import com.xr.run.service.SysPermissionService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.constants.ErrorEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("syspermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @DeleteMapping("del")
    @RequiresPermissions("permission:delete")
    public JSONObject delSysPermission(@RequestBody SysPermission sysPermission){
        System.out.println(sysPermission);
        Integer count = sysPermissionService.delSysPermissionPage(sysPermission);
        if(count==0){
            return CommonUtil.successJson(0);
        }
        return CommonUtil.successJson(1);
    }
    @PostMapping("add")
    public JSONObject addSysPermission(@RequestBody SysPermission sysPermission){
        try {
            System.out.println(sysPermission);
            Integer i = sysPermissionService.addSysPermission(sysPermission);
            if(i>0){
                return CommonUtil.successJson(1);
            }else{
                CommonUtil.successJson(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.errorJson(ErrorEnum.E_500);
        }
        return null;
    }
    @GetMapping("get/{pageNum}/{pageRow}")
    public JSONObject addSysPermission(@PathVariable Integer pageNum, @PathVariable Integer pageRow, String name, String re){
        try {
            pageNum=pageNum<1?1:pageNum;
            pageRow=pageRow<5?5:pageRow;
            Page page=new Page(pageNum,pageRow);
            Integer required=3;
            System.out.println(re);
            if("必选".equals(re)){
                required=1;
            }if("非必选".equals(re)){
                required=2;
            }
            page.setSearchCount(true);
            IPage<SysPermission> sysPermissionPage = sysPermissionService.findSysPermissionPage(page, name, required);
            return CommonUtil.successJson(sysPermissionPage);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.errorJson(ErrorEnum.E_500);
        }

    }

}
