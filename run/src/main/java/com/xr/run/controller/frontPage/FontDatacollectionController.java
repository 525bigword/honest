package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.Wind;
import com.xr.run.service.DatacollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("qt/datacollection")
public class FontDatacollectionController {
    @Autowired
    private DatacollectionService datacollectionService;
    @GetMapping("get")
    public String getQfwyList(String title, Integer pageNo, Integer pageSize){
        Page page = new Page(pageNo,pageSize);
        IPage<Datacollection> dataConllection = datacollectionService.findDataConllection(page, title);
        String string = JSON.toJSONString(dataConllection);
        return string;
    }
}
