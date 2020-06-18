package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.service.DatacollectionService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/datacollection")
public class DatacollectionController {
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @Value("${file.uploadFolder}")
    private String realBasePath;
    @Autowired
    private DatacollectionService datacollectionService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findDataConllection(@PathVariable Integer pageNum,String dTitle, @PathVariable Integer pageRow){
        if(dTitle==null){
            dTitle="";
        }
        System.out.println("进来了");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Datacollection> page=new Page(pageNum,pageRow);
        IPage<Datacollection> sysStaffAll = datacollectionService.findDataConllection(page,dTitle);
        return CommonUtil.successJson(sysStaffAll);
     }
    @RequestMapping("update")
    public JSONObject updateDatacollection(Datacollection datacollection)  {
        String filePath = datacollectionService.findDatacollectionByFile(datacollection.getDid());
        try{
            File file=new File(realBasePath+filePath);
            if(file.exists()){
                file.delete();
            }}catch (Exception e){
            e.printStackTrace();
        }
        datacollectionService.updateDataConllectionByDid(datacollection);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteDatacollectionByDid(@RequestBody int[] did)  {
        if (did.length==1){
            datacollectionService.deleteDataConllectionByDid(did[0]);
        }else{
            for (int i = 0; i < did.length; i++) {
                datacollectionService.deleteDataConllectionByDid(did[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertDatacollection(Datacollection datacollection)  {
        datacollectionService.insertDataConllection(datacollection);
        return CommonUtil.successJson("新增成功!");
    }
}
