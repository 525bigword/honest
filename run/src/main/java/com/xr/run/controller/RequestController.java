package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.IpUtil;
import com.xr.run.util.ResponseResult;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RequestController {
    @GetMapping("demo")
    public JSONObject demo(HttpServletRequest request,String arg){
        ResponseResult result=new ResponseResult();
        String ipAddress = IpUtil.getIpAddr(request);
        System.out.println(ipAddress);
        return CommonUtil.successJson(ipAddress);
    }
    @GetMapping("moble")
    public void moble(Device device){
        if (device.isMobile()) {
            System.out.println("========请求来源设备是手机！========");
        } else if (device.isTablet()) {
            System.out.println("========请求来源设备是平板！========");
        } else if(device.isNormal()){
            System.out.println("========请求来源设备是PC！========");
        }else {
            System.out.println("========请求来源设备是其它！========");
        }

    }
}
