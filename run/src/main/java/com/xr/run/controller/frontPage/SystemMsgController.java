/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SystemMsgController
 * Author:   TSYH
 * Date:     2020-07-21 22:40
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvDuty;
import com.xr.run.entity.SystemMessage;
import com.xr.run.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *通知公告
 * @author TSYH
 * @create 2020-07-21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/msg")
public class SystemMsgController {


    @Autowired
    private SystemMessageService systemMessageService;

    //得到通知公告列表
    @RequestMapping("/msgList")
    public String msgList(String title, Integer pageNo, Integer pageSize){
        IPage<SystemMessage> systemMessage = systemMessageService.findSystemMessage(new Page(pageNo, pageSize), title, "");
        return JSON.toJSONString(systemMessage);
    }

    //得到指定通知公告
    @RequestMapping("/getMsgId")
    public String getMsgId(String id){
        if (id != null && !id.equals("null") && !id.equals("")) {
            SystemMessage  systemMessage=  systemMessageService.findSystemMessageByAid(Integer.parseInt(id));
            String s = JSON.toJSONString(systemMessage);
            return s;
        }
        return "null";
    }





















}