package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPostService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("syspost")
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;
    @PostMapping("get/{pageNum}/{pageRow}")
    public JSONObject getPage(@PathVariable Integer pageNum, @PathVariable Integer pageRow,  @RequestBody JSONObject jsonObject){
        System.out.println(pageNum);
        System.out.println(pageRow);
        System.out.println(jsonObject.get("message"));
        System.out.println(jsonObject.get("pname"));
        System.out.println(jsonObject.get("mids"));
        pageNum=pageNum<0||null==pageNum?0:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page page=new Page(pageNum,pageRow);
        List<SysPost> sysPostPage = sysPostService.findSysPostPage(pageNum,pageRow, jsonObject);
        Integer count = sysPostService.findSysPostPage(jsonObject);
        Map map=new HashMap();
        map.put("count",count);
        map.put("data",sysPostPage);
        return CommonUtil.successJson(map);
    }
}
