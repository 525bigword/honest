package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysMechanismService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysmechanism")
public class SysMechanismController {
    @Autowired
    private SysMechanismService sysMechanismService;


    @GetMapping("get/{pageNum}/{pageRow}")
    @RequiresPermissions("mechanism:list")
    @ApiOperation(value = "部门查询", notes = "查询部门")
    public JSONObject getSysmechanismPage(@PathVariable Integer pageNum, @PathVariable Integer pageRow, String mechanism, String principal, String staus) {
        pageNum = pageNum < 0 ? 0 : pageNum;
        pageRow = pageRow < 5 ? 5 : pageRow;

        Map<String, Object> sysMechanismPage = sysMechanismService.findSysMechanismPage(pageNum, pageRow, mechanism, principal, staus);
        return CommonUtil.successJson(sysMechanismPage);
    }

    @GetMapping("get")
    @RequiresPermissions(value ={"mechanism:list","mechanism:add","mechanism:update","staff:update","staff:add","staff:list","ppost:add","ppost:update","ppost:list"},logical = Logical.OR)
    public JSONObject getSysmechanismAll() {
        List<SysMechanism> sysMechanismAll = sysMechanismService.findSysMechanismAll();
        return CommonUtil.successJson(sysMechanismAll);
    }
    //根据用户查询部门ID
    @GetMapping("getmname/{id}")
    public JSONObject getSysmechanismNameById(@PathVariable Integer id){
        SysStaff sysMechanismNameById = sysMechanismService.findSysMechanismNameById(id);
        return CommonUtil.successJson(sysMechanismNameById.getMid());
    }
    @RequestMapping("/findSysMechanisms")
    public JSONObject findSysMechanisms() {
        List<SysMechanism> sysMechanisms = sysMechanismService.findSysMechanisms();
        return CommonUtil.successJson(sysMechanisms);
    }

    @PostMapping("add")
    @RequiresPermissions("mechanism:add")
    public JSONObject addSysmechanism(SysMechanism sysMechanism) {
        sysMechanismService.addSysMechanism(sysMechanism);
        return CommonUtil.successJson();
    }

    @DeleteMapping("del/{mid}")
    @RequiresPermissions("mechanism:delete")
    public JSONObject delSysmechanism(@PathVariable Integer mid) {
        System.out.println(mid);
        Integer i = sysMechanismService.delSysMechanism(mid);
        if (i == 0) {
            return CommonUtil.successJson(1);
        }
        return CommonUtil.successJson(0);
    }

    @PutMapping("update/{mid}")
    @RequiresPermissions("mechanism:update")
    public JSONObject upSysmechanismStaus(@PathVariable Integer mid) {
        sysMechanismService.unDelSysMechanism(mid);
        return CommonUtil.successJson(1);
    }

    @PutMapping("update")
    @RequiresPermissions("mechanism:update")
    public JSONObject upSysmechanism(SysMechanism sysMechanism) {
        System.out.println(sysMechanism);
        sysMechanismService.upSysmechanism(sysMechanism);
        return CommonUtil.successJson(1);
    }

    @GetMapping("getAll")
    public ResponseResult getAll() {
        List<SysMechanism> list = sysMechanismService.getAll();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

}
