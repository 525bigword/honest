package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SystemMessage;
import com.xr.run.service.SystemMessageService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/systemmessage")
public class SystemMessageController {
    @Autowired
    private SystemMessageService systemMessageService;
    @Value("${file.uploadImage}")
    private String realBasePath;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findSystemMessage(@PathVariable Integer pageNum, String articletitle,String describe, @PathVariable Integer pageRow){
        if(articletitle==null){
            articletitle="";
        }
        if(describe==null){
            describe="";
        }
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SystemMessage> page=new Page(pageNum,pageRow);
        IPage<SystemMessage> sysStaffAll = systemMessageService.findSystemMessage(page,articletitle,describe);
        return CommonUtil.successJson(sysStaffAll);
    }

    @RequestMapping("update")
    public JSONObject updateSystemMessage(SystemMessage systemMessage)  {
        if(systemMessage.getPicturename().equals("1")){
            systemMessageService.updateSystemMessageByAidNoFile(systemMessage);
        }else {
            String filePath = systemMessageService.findSystemMessageByFile(systemMessage.getAid());
            try {
                File file = new File(realBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            systemMessageService.updateSystemMessageByAid(systemMessage);
        }
        return CommonUtil.successJson("修改成功!");
    }

    @RequestMapping("delete")
    public JSONObject deleteSystemMessageByAid(@RequestBody int[] aid)  {
        if (aid.length==1){
            systemMessageService.deleteSystemMessageByAid(aid[0]);
        }else{
            for (int i = 0; i < aid.length; i++) {
                systemMessageService.deleteSystemMessageByAid(aid[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertSystemMessage(SystemMessage systemMessage)  {
        systemMessageService.insertSystemMessage(systemMessage);
        return CommonUtil.successJson("新增成功!");
    }
}
