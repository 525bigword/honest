package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.service.PostriskcombingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("qt/fontpostriskcombing")
public class FontPostRiskCombingController {
    @Autowired
    private PostriskcombingService postriskcombingService;
    @GetMapping("get")
    public String get(String title, Integer pageNo, Integer pageSize){
        Page page = new Page(pageNo,pageSize);
        IPage<Postriskcombing> postriskCombingIndex = postriskcombingService.findPostriskCombingIndex(page, title);
        String string = JSON.toJSONString(postriskCombingIndex);
        return string;
    }
}
