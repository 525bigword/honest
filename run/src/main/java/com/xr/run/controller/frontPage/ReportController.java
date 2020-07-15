/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ReportController
 * Author:   TSYH
 * Date:     2020-07-15 14:26
 * Description: 纪检报表
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.DcpReportMapper;
import com.xr.run.entity.DcpReport;
import com.xr.run.service.DcpReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈纪检报表〉
 *
 * @author TSYH
 * @create 2020-07-15
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/report")
public class ReportController {
    @Autowired
    private DcpReportService dcpReportService;

    //查询纪检报表
    @RequestMapping("/getReport")
    public String getReport(Integer pageNo,Integer pageSize,String title){
        Page page = new Page(pageNo,pageSize);
        IPage<DcpReport> dcpReportIndex = dcpReportService.findDcpReportIndex(page, title);
        String s = JSON.toJSONString(dcpReportIndex);
        return s;
    }

    //根据id查询纪检报表
    @RequestMapping("/getReportById")
    public String getReportById(String id){
        if(id!=null&&!id.equals("")&&!id.equals("null")) {
          DcpReport dcpReport  =  dcpReportService.findDcpReportById(Integer.parseInt(id));
          String s = JSON.toJSONString(dcpReport);
          return s;
        }
        return "null";
    }





























}