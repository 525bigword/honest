package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvDuty;
import com.xr.run.service.SpvDutyService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/spvduty")
public class SpvDutyController {
    @Autowired
    private SpvDutyService spvDutyService;
    @Value("${file.uploadDuty}")
    private String realBasePath;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findSpvDuty(@PathVariable Integer pageNum, String dutyTitle, @PathVariable Integer pageRow){
        if(dutyTitle==null){
            dutyTitle="";
        }
        System.out.println("findDataConllection");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SpvDuty> page=new Page(pageNum,pageRow);
        IPage<SpvDuty> sysStaffAll = spvDutyService.findSpvDuty(page,dutyTitle);
        return CommonUtil.successJson(sysStaffAll);
    }
    @RequestMapping("update")
    public JSONObject updateSpvDuty(SpvDuty spvDuty)  {
        System.out.println(spvDuty.getDutyAccessory());
        if(spvDuty.getDutyAccessory().equals("1")){
            spvDutyService.updateSpvDutyByDid(spvDuty);
        }else {
            String filePath = spvDutyService.findSpvDutyByFile(spvDuty.getDid());
            try {
                File file = new File(realBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            spvDutyService.updateSpvDutyFileByDid(spvDuty);
        }
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteSpvDutyByDid(@RequestBody int[] did)  {
        if (did.length==1){
            spvDutyService.deleteSpvDutyByDid(did[0]);
        }else{
            for (int i = 0; i < did.length; i++) {
                spvDutyService.deleteSpvDutyByDid(did[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertSpvDuty(SpvDuty spvDuty)  {
        spvDutyService.insertSpvDuty(spvDuty);
        return CommonUtil.successJson("新增成功!");
    }
}
