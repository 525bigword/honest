package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.Wind;
import com.xr.run.service.WindService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wind")
public class WindController {
    @Autowired
    private WindService windService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findWind(@PathVariable Integer pageNum, String wTitle, @PathVariable Integer pageRow){
        if(wTitle==null){
            wTitle="";
        }
        System.out.println("findWind");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Wind> page=new Page(pageNum,pageRow);
        IPage<Wind> wind = windService.findWind(page, wTitle);
        return CommonUtil.successJson(wind);
    }
}
