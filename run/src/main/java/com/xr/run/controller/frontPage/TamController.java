/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TamController
 * Author:   TSYH
 * Date:     2020-07-16 22:00
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.tam.Smokestyle;
import com.xr.run.service.tam.SmokestyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-16
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/tamSmoke")
public class TamController{

    @Autowired
    private SmokestyleService smokestyleService;

    @RequestMapping("/getSmoke")
    public String getSmoke(String title,Integer pageNo,Integer pageSize){

        IPage<Smokestyle> allSmokestyle = smokestyleService.findAllSmokestyle1(new Page(pageNo,pageSize),title);

        return JSON.toJSONString(allSmokestyle);
    }









}