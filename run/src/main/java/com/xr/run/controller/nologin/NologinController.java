package com.xr.run.controller.nologin;

import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysPpost;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.daily.LetterReport;
import com.xr.run.service.daily.LetterReportService;
import com.xr.run.service.duty.HonestConversationService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("nologin")
@RestController
public class NologinController {
    @Autowired
    private LetterReportService letterreportService;
    @Autowired
    private HonestConversationService honestConversationService;
    /*不登录时获取单位*/
    @RequestMapping("getFileGroup")
    public ResponseResult getFileGroup(Integer parent){
        ResponseResult result=new ResponseResult();
        List<SysMechanism> findallunit = honestConversationService.findallunit(parent);
        result.getInfo().put("list",findallunit);
        return result;
    }
    @RequestMapping("addletter")
    public ResponseResult addletter(LetterReport letterReport){
        letterreportService.addletter(letterReport);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","举报成功");
        return result;

    }
    @RequestMapping("initdept")
    public ResponseResult initdept(Integer mid){
        ResponseResult result=new ResponseResult();
        List<SysPpost> findalldept = honestConversationService.findalldept(mid);
        result.getInfo().put("list",findalldept);
        return result;
    }
    @RequestMapping("initemploy")
    public ResponseResult initemploy(Integer ppid){
        ResponseResult result=new ResponseResult();
        List<SysStaff> findallperson = honestConversationService.findallperson(ppid);
        result.getInfo().put("list",findallperson);
        return result;
    }
    /*信访举报 填写受理登记表*/
    @RequestMapping("letterreporter")
    public ResponseResult letterreporter(){
        ResponseResult result=new ResponseResult();
        result.getInfo().put("letterid", "XF"+ UUID.randomUUID().toString());
        return result;
    }
}
