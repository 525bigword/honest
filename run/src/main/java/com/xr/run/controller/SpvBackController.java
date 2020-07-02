package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SpvDuty;
import com.xr.run.service.SpvBackService;
import com.xr.run.service.SpvDutyService;
import com.xr.run.util.AsposeUtil;
import com.xr.run.util.CommonUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/spvback")
public class SpvBackController {
    @Autowired
    private SpvBackService spvBackService;
    @Autowired
    private SpvDutyService spvDutyService;
    @Value("${file.uploadDuty}")
    private String realBasePath;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findSpvBack(@PathVariable Integer pageNum, Integer bid, Integer did, @PathVariable Integer pageRow){
        System.out.println("findSpvBack");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<SpvBack> page=new Page(pageNum,pageRow);
        IPage<SpvBack> spvBack=null;
        if(did!=0&&did!=null){
            spvBack=spvBackService.findSpvBackByDid(page,did);
        }else{
            spvBack=spvBackService.findSpvBack(page,bid);
        }
        return CommonUtil.successJson(spvBack);
    }
    @RequestMapping("update")
    public JSONObject updateSpvBack(SpvBack spvBack)  {
        System.out.println(spvBack);
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
            String pdf = getPdf(spvBack.getBackAccessory());
            spvBack.setBPdf(pdf);
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
    @RequestMapping("updatestatusall")
    public JSONObject updateStatusall(SpvBack spvBack)  {
        System.out.println(spvBack);
        String[] split = spvBack.getBackType().split(",");
        SpvBack spvBack1=new SpvBack();
        spvBack1.setStatus(spvBack.getStatus());
        if (split.length==1){
            spvBack1.setSid(Integer.parseInt(split[0]));
            spvBackService.updateStatusBySid(spvBack1);
        }else{
            for (int i = 0; i < split.length; i++) {
                spvBack1.setSid(Integer.parseInt(split[i]));
                spvBackService.updateStatusBySid(spvBack1);
            }
        }//修改子部门信息状态
        //修改父部门
        SpvDuty spvDuty=new SpvDuty();
        spvDuty.setDid(spvBack.getbid());
        spvDuty.setStatus(spvBack.getSid());
        spvDutyService.updateStatusByDid(spvDuty);
        return CommonUtil.successJson("修改成功!");
    }

    @RequestMapping("delete")
    public JSONObject deleteSpvBackBySid(int sid){
        spvBackService.deleteSpvBackBySid(sid);
        return CommonUtil.successJson("删除成功!");
    }

    public String getPdf(String path){
        if(path.contains(".doc")||path.contains(".docx")){
            String path1 = path.substring(0, path.lastIndexOf("."));
            String url=path1+".pdf";
            System.out.println(url);
            AsposeUtil.doc2pdf(realBasePath+path,realBasePath+url);
            return url;
        }
        return null;
    }

}
