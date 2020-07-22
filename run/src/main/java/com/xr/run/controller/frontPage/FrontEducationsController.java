/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: FrontEducationsController
 * Author:   TSYH
 * Date:     2020-07-13 20:32
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.RdWorkPlan;
import com.xr.run.entity.educationpolitics.EducationPolitics;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.service.educationPolitics.EducationPoliticsService;
import com.xr.run.util.CommonUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 廉政教育
 * @author TSYH
 * @create 2020-07-13
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/educations")
public class FrontEducationsController {

    //廉政教育
    @Autowired
    private EducationPoliticsService educationPoliticsService;

    public IPage<EducationPolitics> getList(Page page,String title){
        return  educationPoliticsService.findwhereEducationIndex(page,title);
    }


    @GetMapping("/getEduList")
    public String getEduList(String type,String title, Integer pageNo, Integer pageSize){
        if(type!=null&&!type.equals("")&&!type.equals("null")) {
            if (Integer.parseInt(type) == 0) {
                //廉政要闻
                Page page = new Page(pageNo, pageSize);
                IPage<EducationPolitics> educationPolitics = educationPoliticsService.findEducationIndexByType(page, title, "廉政要闻");
                String jsonString = JSON.toJSONString(educationPolitics);
                return jsonString;
            } else if (Integer.parseInt(type) == 1) {
                //文件制度
                Page page = new Page(pageNo, pageSize);
                IPage<EducationPolitics> educationPolitics = educationPoliticsService.findEducationIndexByType(page, title, "文件制度");
                String jsonString = JSON.toJSONString(educationPolitics);
                return jsonString;
            } else if (Integer.parseInt(type) == 2) {
                //领导讲话
                Page page = new Page(pageNo, pageSize);
                IPage<EducationPolitics> educationPolitics = educationPoliticsService.findEducationIndexByType(page, title, "领导讲话");
                String jsonString = JSON.toJSONString(educationPolitics);
                return jsonString;
            } else {
                //警钟长鸣
                Page page = new Page(pageNo, pageSize);
                IPage<EducationPolitics> educationPolitics = educationPoliticsService.findEducationIndexByType(page, title, "警钟长鸣");
                String jsonString = JSON.toJSONString(educationPolitics);
                return jsonString;
            }
        }
          return "null";
    }


    @RequestMapping("/getEduById")
    public String getEduById(String id){
        if (id != null && !id.equals("null") && !id.equals("")) {
            EducationPolitics educationPolitics = educationPoliticsService.finsEduById(Integer.parseInt(id));
            String s = JSON.toJSONString(educationPolitics);
            return s;
        }
        return "null";
    }






















}