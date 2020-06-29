package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysLog;
import com.xr.run.service.StaticHtmlService;
import com.xr.run.service.SysLogService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("syslog")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;
    @GetMapping("get/{pageNum}/{pageRow}")
    public JSONObject get(@PathVariable Integer pageNum, @PathVariable Integer pageRow, String begin,String end,String ip,String username){
        System.out.println(pageNum);
        System.out.println(pageRow);
        System.out.println(begin);
        System.out.println(end);
        System.out.println(ip);
        System.out.println(username);
        Date begindate=null;
        Date enddate=null;
        pageNum=pageNum<1?1:pageNum;
        pageRow=pageRow<5?5:pageRow;
        Page page=new Page(pageNum,pageRow);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        IPage<SysLog> sysLog=null;
        try {
            if(begin!=null&&end!=null){
                begindate = simpleDateFormat.parse(begin);
                enddate = simpleDateFormat.parse(end);
            }

            sysLog = sysLogService.findSysLog(page, begindate, enddate, ip, username);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return CommonUtil.successJson(sysLog);
    }
}
