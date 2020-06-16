package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.SysMechanismService;
import com.xr.run.util.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysmechanism")
public class SysMechanismController {
    @Autowired
    private SysMechanismService sysMechanismService;


    @GetMapping("get/{pageNum}/{pageRow}")
    @ApiOperation(value = "部门查询",notes = "查询部门")
    public JSONObject getSysmechanismPage(@PathVariable Integer pageNum, @PathVariable Integer pageRow, String mechanism,String principal,String staus){
        pageNum=pageNum<0?0:pageNum;
        pageRow=pageRow<5?5:pageRow;

        Map<String, Object> sysMechanismPage = sysMechanismService.findSysMechanismPage(pageNum, pageRow, mechanism, principal, staus);
        return CommonUtil.successJson(sysMechanismPage);
    }

    @GetMapping("get")
    public JSONObject getSysmechanismAll(){
        List<SysMechanism> sysMechanismAll = sysMechanismService.findSysMechanismAll();
        return CommonUtil.successJson(sysMechanismAll);
    }
}
