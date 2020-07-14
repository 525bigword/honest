package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.Wind;
import com.xr.run.service.DatacollectionService;
import com.xr.run.service.StaticHtmlService;
import com.xr.run.service.SysStaffService;
//import com.xr.run.util.AsposeUtil;
import com.xr.run.util.AsposeUtil;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/datacollection")
public class DatacollectionController {
    @Value("${file.uploadFolder}")
    private String realBasePath;
    @Value("${file.uploadVideo}")
    private String videoBasePath;
    @Value("${file.uploadVideo}")
    private String destPath;
    @Autowired
    private StaticHtmlService staticHtmlService;
    @Autowired
    private DatacollectionService datacollectionService;
    @Autowired
    private SysStaffService sysStaffService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    @RequiresPermissions("datacollection:list")
    public JSONObject findDataConllection(@PathVariable Integer pageNum,String dTitle, @PathVariable Integer pageRow){
        if(dTitle==null){
            dTitle="";
        }
        System.out.println("哪个");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Datacollection> page=new Page(pageNum,pageRow);
        IPage<Datacollection> sysStaffAll = datacollectionService.findDataConllection(page,dTitle);
        return CommonUtil.successJson(sysStaffAll);
    }
    @RequestMapping("update")
    @RequiresPermissions("datacollection:update")
    public JSONObject updateDatacollection(Datacollection datacollection,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        System.out.println(datacollection.getDFile());
        Datacollection data= datacollectionService.findDatacollectionById(datacollection.getDid());
        staticHtmlService.deleteHtmlPage(data.getDTitle());
        if(datacollection.getDFile().equals("1")){
            datacollectionService.updateDataConllectionByFile(datacollection);
        }else if(datacollection.getDVideo().equals("1")){
            String filePath = datacollectionService.findDatacollectionByFile(datacollection.getDid());
            try {
                File file = new File(realBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String pdf = getPdf(datacollection.getDFile());
            datacollection.setDPdf(pdf);
            datacollectionService.updateDataConllectionByDid(datacollection);
        }else if(datacollection.getDFile().equals("2")){
            String filePath = datacollectionService.findDatacollectionBydVideo(datacollection.getDid());
            try {
                File file = new File(videoBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            datacollectionService.updateDataConllectionByVideo(datacollection);
        }else{
            String filePath = datacollectionService.findDatacollectionByFile(datacollection.getDid());
            try {
                File file = new File(realBasePath + filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("删除文件后");
            String pdf = getPdf(datacollection.getDFile());
            datacollection.setDPdf(pdf);
            String videoPath = datacollectionService.findDatacollectionBydVideo(datacollection.getDid());
            try {
                File file = new File(videoBasePath + videoPath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            datacollectionService.updateDataConllectionByVideoAndFile(datacollection);
        }
        thymeleaftest(datacollection,httpServletRequest,httpServletResponse);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    @RequiresPermissions("datacollection:delete")
    public JSONObject deleteDatacollectionByDid(@RequestBody int[] did)  {
        if (did.length==1){
            Datacollection data= datacollectionService.findDatacollectionById(did[0]);
            staticHtmlService.deleteHtmlPage(destPath+"\\182\\184\\"+data.getDid()+".html");
            Datacollection datacollectionById = datacollectionService.findDatacollectionById(did[0]);
            deleteFile(datacollectionById);
            datacollectionService.deleteDataConllectionByDid(did[0]);
        }else{
            for (int i = 0; i < did.length; i++) {
                Datacollection data= datacollectionService.findDatacollectionById(did[i]);
                staticHtmlService.deleteHtmlPage(data.getDTitle());
                staticHtmlService.deleteHtmlPage(destPath+"\\182\\184\\"+data.getDid()+".html");
                Datacollection datacollectionById = datacollectionService.findDatacollectionById(did[i]);
                deleteFile(datacollectionById);
                datacollectionService.deleteDataConllectionByDid(did[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    @RequiresPermissions("datacollection:add")
    public JSONObject insertDatacollection(Datacollection datacollection, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        String pdf = getPdf(datacollection.getDFile());
        datacollection.setDPdf(pdf);
        datacollectionService.insertDataConllection(datacollection);
        thymeleaftest(datacollection,httpServletRequest,httpServletResponse);
        return CommonUtil.successJson("新增成功!");
    }

    public String getPdf(String path){
        if(path.contains(".doc")||path.contains(".docx")){
            String path1 = path.substring(0, path.lastIndexOf("."));
            String url=path1+".pdf";
        AsposeUtil.doc2pdf(realBasePath+path,realBasePath+url);
            return url;
        }
        return null;
    }
    public void thymeleaftest(Datacollection datacollection,HttpServletRequest req,HttpServletResponse resp){
        ModelAndView modelAndView=new ModelAndView();
            Datacollection datacollectionById = datacollectionService.findDatacollectionById(datacollection.getDid());
            String name = sysStaffService.findSysStaffByIdToName(datacollectionById.getdCreateId());
            modelAndView.addObject("dFileName",datacollectionById.getDFileName());
            modelAndView.addObject("dPdf",datacollectionById.getDPdf());
            modelAndView.addObject("dFile",datacollectionById.getDFile());
            modelAndView.addObject("title",datacollectionById.getDTitle());
            modelAndView.addObject("time", datacollectionById.getDCreateTime());
            modelAndView.addObject("name", name);
        modelAndView.setViewName("183/Info");

        staticHtmlService.genHtmlPage(destPath+"\\182\\184\\",modelAndView,req,resp,datacollectionById.getDid()+"");
    }
    public void deleteFile(Datacollection datacollectionById){
        try {
            File file = new File(videoBasePath + datacollectionById.getDVideo());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File(realBasePath + datacollectionById.getDPdf());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File(realBasePath + datacollectionById.getDFile());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
