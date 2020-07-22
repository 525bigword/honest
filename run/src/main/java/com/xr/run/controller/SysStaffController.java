package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.HomePageSevice;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.constants.Constants;
import com.xr.run.util.constants.ErrorEnum;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private HomePageSevice homePageSevice;

    @GetMapping("del/{id}")
    public JSONObject yjDel(@PathVariable Integer id){
        sysStaffService.yjDelSysStaffById(id);
        return CommonUtil.successJson();
    }
    @GetMapping("hf/{id}")
    public JSONObject hfSysStaff(@PathVariable Integer id){
        sysStaffService.hfSysStaff(id);
        return CommonUtil.successJson();
    }

    @GetMapping("get/{sid}")
    public JSONObject getSysStaffBySid(@PathVariable Integer sid){
        SysStaff sysStaffBySid = sysStaffService.findSysStaffBySid(sid);
        return CommonUtil.successJson(sysStaffBySid);
    }
    @PostMapping("delete")
    @RequiresPermissions("staff:delete")
    public JSONObject delSysStaff(String str){
        Integer integer = sysStaffService.delSysStaff(str);
        System.out.println("=========================="+integer);
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
    @GetMapping("gets/{pid}/{mid}")
    @RequiresPermissions(value = {"mechanism:list","mechanism:add","mechanism:update"},logical = Logical.OR)
    public JSONObject findstatffByPidAndMid(@PathVariable Integer pid,@PathVariable Integer mid){
        List<SysStaff> sysStaffAll = sysStaffService.findstatffByPid(pid,mid);
        return CommonUtil.successJson(sysStaffAll);
        //return sysStaffAll;
    }
    @GetMapping("gets/{mid}")
    public JSONObject findstatffByPid(@PathVariable Integer mid){
        List<SysStaff> sysStaffAll = sysStaffService.findstatffByMid(mid);
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
     * 生成前台首页
     */
    @GetMapping("/createIndex")
    @RequiresPermissions("index:add")
    public JSONObject createIndex(){
        try {
            homePageSevice.loading();

            return CommonUtil.successJson();
        }catch (Exception e){
            return CommonUtil.errorJson(ErrorEnum.E_500);
        }
    }
    /**
     * 登录
     */
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");
        JSONObject jsonObject = sysStaffService.authLogin(requestJson);
        //创建首页
//        homePageSevice.loading();
        return jsonObject;
    }
    /**
     * 登录
     */
    @PostMapping("/login")
    public String auth(@RequestParam String username, @RequestParam String password, HttpSession session) {
//
//        JSONObject jsonObject = sysStaffService.authLogin(requestJson);
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        //创建首页
//        homePageSevice.loading();
        return session.getId();
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

    public static void main(String[] args) {
        SimpleHash md5 = new SimpleHash("MD5", "123456", "", 2);
        System.out.println(md5.toString());
    }


    /**
     * 根据部门查用户集合
     * Author mpy
     * @param mid
     * @return
     */
    @RequestMapping("findSysStaffByMid")
    public JSONObject findSysStaffByMid(int mid){
        List<SysStaff> sysStaffByMid = sysStaffService.findSysStaffByMid(mid);
        return  CommonUtil.successJson(sysStaffByMid);
    }

}
