package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.service.DatacollectionService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/datacollection")
public class DatacollectionController {
    @Autowired
    private DatacollectionService datacollectionService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findDataConllection(@PathVariable Integer pageNum, @PathVariable Integer pageRow){
        System.out.println("进来了");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Datacollection> page=new Page(pageNum,pageRow);
        IPage<Datacollection> sysStaffAll = datacollectionService.findDataConllection(page);
        return CommonUtil.successJson(sysStaffAll);
        }
}
