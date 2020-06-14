package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
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
    @GetMapping("/get/{pageNum}/{pageRow}")
    public IPage<SysStaff> findstatffs(@PathVariable Integer pageNum,@PathVariable Integer pageRow){
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SysStaff> page=new Page(pageNum,pageRow);
        IPage<SysStaff> sysStaffAll = sysStaffService.findSysStaffAll(page);
        return sysStaffAll;
    }
    /**
     * 登录
     */
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");
        return sysStaffService.authLogin(requestJson);
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
