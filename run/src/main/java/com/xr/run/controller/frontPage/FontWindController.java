package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.*;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.service.DatacollectionService;
import com.xr.run.service.WindService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qt/qtwind")
public class FontWindController {
    @Autowired
    private DatacollectionService datacollectionService;
    @Autowired
    private WindService windService;

    @GetMapping("get")
    public String getQfwyList(String title, Integer pageNo, Integer pageSize){
        Page page = new Page(pageNo,pageSize);
        IPage<Wind> wind = windService.findWind(page, title);
        String string = JSON.toJSONString(wind);
        return string;
    }
}
