package com.xr.run.controller.inspect;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.inspect.Report;
import com.xr.run.service.inspect.DcpReportService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/inspect/report")
@RestController
public class DcpReportAction {
    @Autowired
    private DcpReportService dcpReportService;
    @RequestMapping("list")
    public ResponseResult list(){
        List<Report> list = dcpReportService.list();
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;

    }
    @RequestMapping("findwhere")
    public ResponseResult findwhere(Report report){
        List<Report> list = dcpReportService.fintBywhere(report);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;

    }
    @RequestMapping("addreport")
    public ResponseResult addreport(Report report){
        dcpReportService.addreport(report);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
    @RequestMapping("delReport")
    public ResponseResult delReport(String test){
        System.out.println("test"+test);
        List<Report> reports = JSON.parseArray(test, Report.class);
        for(Report report : reports){ // Exception
            System.out.println("report"+report.getId());
            dcpReportService.delReport(report.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
    @RequestMapping("gxpost")
    public ResponseResult gxpost(Report report){
        ResponseResult result=new ResponseResult();
        dcpReportService.gxpost(report);
        result.getInfo().put("message","更新成功");
        return result;
    }
}
