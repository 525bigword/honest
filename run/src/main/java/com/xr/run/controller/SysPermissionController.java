package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysPermission;
import com.xr.run.service.SysPermissionService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("syspermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping("add")
    public String addSysPermission(SysPermission sysPermission){
        try {
            sysPermissionService.addSysPermission(sysPermission);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "filid";
        }

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
