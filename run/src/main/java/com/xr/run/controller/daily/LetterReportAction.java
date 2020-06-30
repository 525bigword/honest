package com.xr.run.controller.daily;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.daily.LetterReport;
import com.xr.run.service.daily.LetterReportService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/daily/letter")
public class LetterReportAction {
    @Autowired
    private LetterReportService letterReportService;
    @RequestMapping("xfbh")
    public ResponseResult xfbh(){

        ResponseResult result=new ResponseResult();
        String xfbh = letterReportService.xfbh();
        result.getInfo().put("xfbh",xfbh);
        return result;
    };
    @RequestMapping("listLetter")
    public ResponseResult list(LetterReport letterReport){
        ResponseResult result=new ResponseResult();
        List<LetterReport> list = letterReportService.list(letterReport);
        result.getInfo().put("list",list);
        return result;
    }
    @RequestMapping("addletter")
    public ResponseResult addletter(LetterReport letterReport){
        letterReportService.addletter(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    @RequestMapping("delletter")
    public ResponseResult delletter(String test){
        System.out.println("test"+test);
        List<LetterReport> letterReports = JSON.parseArray(test, LetterReport.class);
        for(LetterReport letterReport : letterReports){ // Exception
            System.out.println("letterReport"+letterReport.getLid());
            letterReportService.deletter(letterReport.getLid());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
    @RequestMapping("findbyName")
    public ResponseResult findbyName(LetterReport letterReport){
        System.out.println("letterRport"+letterReport);
        ResponseResult result=new ResponseResult();
        List<LetterReport> list = letterReportService.findbyName(letterReport);
        result.getInfo().put("list",list);
        return result;
    }
    @RequestMapping("listLetterByid")
    public ResponseResult listLetterByid(Integer lid){
        ResponseResult result=new ResponseResult();
        List<LetterReport> list = letterReportService.listLetterByid(lid);
        result.getInfo().put("list",list);
        return result;
    }
    @RequestMapping("shbc")
    public ResponseResult shbc(LetterReport letterReport) {
letterReportService.shbc(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","保存成功");
        return result;
    }
    @RequestMapping("tjshme")
    public ResponseResult tjshme(Integer lid){
        letterReportService.tjshme(lid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","提交审核成功");
        return result;
    }
   @RequestMapping("turndept")
    public ResponseResult turndept(Integer lid){
        letterReportService.turndept(lid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","转办部门成功");
        return result;
    }
    /*监察科自办结果*/
    @RequestMapping("kezhangbc")
    public ResponseResult kezhangbc(LetterReport letterReport){
        letterReportService.kezhangbc(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","自办处理完成");
        return result;
    }
    /*转办部门处理结果*/
    @RequestMapping("deptbc")
    public ResponseResult deptbc(LetterReport letterReport){
     letterReportService.deptbc(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","已处理完成");
        return result;
    }
    /*用户更新信访内容*/
    @RequestMapping("bjbcmethod")
    public ResponseResult bjbcmethod(LetterReport letterReport){
        letterReportService.bjbcmethod(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","已处理完成");
        return result;
    }
}
