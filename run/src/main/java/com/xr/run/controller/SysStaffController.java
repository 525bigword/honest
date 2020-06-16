package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
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

    @GetMapping("/get")
    public JSONObject findstatffs(){
        List<SysStaff> sysStaffAll = sysStaffService.findSysStaffAll();
        return CommonUtil.successJson(sysStaffAll);
        //return sysStaffAll;
    }

    @GetMapping("/get/{pageNum}/{pageRow}/{staus}")
    public JSONObject findstatffs(@PathVariable Integer pageNum,@PathVariable Integer pageRow,String name,@PathVariable Integer staus){
        System.out.println(name+staus);
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SysStaff> page=new Page(pageNum,pageRow);
        IPage<SysStaff> sysStaffAll = sysStaffService.findSysStaffAll(page,name,staus);
        return CommonUtil.successJson(sysStaffAll);
        //return sysStaffAll;
    }
    /**
     * 登录
     */
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");
        JSONObject jsonObject = sysStaffService.authLogin(requestJson);

        Object principal = SecurityUtils.getSubject().getPrincipal();

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
}
