package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SpvDuty;
import com.xr.run.service.SpvBackService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/spvback")
public class SpvBackController {
    @Autowired
    private SpvBackService spvBackService;
    @Value("${file.uploadDuty}")
    private String realBasePath;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findSpvBack(@PathVariable Integer pageNum, String backTitle, @PathVariable Integer pageRow){
        if(backTitle==null){
            backTitle="";
        }
        System.out.println("findSpvBack");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SpvBack> page=new Page(pageNum,pageRow);
        IPage<SpvBack> spvBack = spvBackService.findSpvBack(page,backTitle);
        return CommonUtil.successJson(spvBack);
    }
    @RequestMapping("update")
    public JSONObject updateSpvBack(SpvBack spvBack)  {
        System.out.println(spvBack.getBackAccessoryName());
        if(spvBack.getBackAccessoryName().equals("1")){
            spvBackService.updateSpvBackBySid(spvBack);
        }else {
            String filePath = spvBackService.findSpvBackByFile(spvBack.getSid());
            try {
                File file = new File(realBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            spvBackService.updateSpvBackFileBySid(spvBack);
        }
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertSpvBack(SpvBack spvBack)  {
        spvBackService.insertSpvBack(spvBack);
        return CommonUtil.successJson("新增成功!");
    }

    @RequestMapping("updatestatus")
    public JSONObject updateStatus(SpvBack spvBack)  {
        spvBackService.updateStatusBySid(spvBack);
        return CommonUtil.successJson("修改成功!");
    }
}
