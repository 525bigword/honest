package com.xr.run.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SpvDuty;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.SpvBackService;
import com.xr.run.service.SpvDutyService;
import com.xr.run.service.StaticHtmlService;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spvduty")
public class SpvDutyController {
    @Autowired
    private SpvDutyService spvDutyService;
    @Autowired
    private SpvBackService spvBackService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private StaticHtmlService staticHtmlService;
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
    public JSONObject updateSpvDuty(SpvDuty spvDuty,HttpServletRequest req,HttpServletResponse resp)  {
        System.out.println(spvDuty.getDutyAccessory());
        if(spvDuty.getDutyAccessoryName().equals("1")){
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
            System.out.println("hgh");
        }
        thymeleafSpvDuty(spvDuty,req,resp);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteSpvDutyByDid(@RequestBody int[] did)  {
        if (did.length==1){
            SpvDuty spvDutyByDid = spvDutyService.findSpvDutyByDid(did[0]);
            staticHtmlService.deleteHtmlPage(spvDutyByDid.getDutyTitle());
            spvDutyService.deleteSpvDutyByDid(did[0]);
        }else{
            for (int i = 0; i < did.length; i++) {
                SpvDuty spvDutyByDid = spvDutyService.findSpvDutyByDid(did[i]);
                staticHtmlService.deleteHtmlPage(spvDutyByDid.getDutyTitle());
                spvDutyService.deleteSpvDutyByDid(did[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertSpvDuty(SpvDuty spvDuty,HttpServletRequest req,HttpServletResponse resp)  {
        spvDutyService.insertSpvDuty(spvDuty);
        thymeleafSpvDuty(spvDuty,req,resp);
        return CommonUtil.successJson("新增成功!");
    }

    @RequestMapping("/getDid")
    public JSONObject getDid(){
        List<SysMechanism> did = spvDutyService.findDid();
        List<Map<String,Object>> map = getMap(did);
        return CommonUtil.successJson(map);
    }
    public List<Map<String,Object>> getMap(List<SysMechanism> list){
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (SysMechanism mechain:list) {
            Map<String,Object> map=new HashMap<>();
            map.put("value",mechain.getMid());
            map.put("label",mechain.getMechanismName());
            List<Map<String,Object>> map1 = getMap(mechain.getChilrenMechanism());
            if(map1.size()>0){
                map.put("children",map1);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping("updatestatus")
    public JSONObject updateStatus(SpvDuty spvDuty,HttpServletRequest req,HttpServletResponse resp)  {
        SpvBack spvBack = new SpvBack();
        spvBack.setBackType(spvDuty.getDutyType());
        spvBack.setBCreateId(spvDuty.getdCreateId());
        spvBack.setDid(spvDuty.getDid());
        String [] split = spvDuty.getBid().split(",");
        spvBack.setStatus(Integer.parseInt(split[0]));
        for (int i = 1; i < split.length; i++) {
            spvBack.setBid(Integer.parseInt(split[i]));
            spvBackService.insertSpvBack(spvBack);
        }
        spvDutyService.updateStatusByDid(spvDuty);
        thymeleafSpvDuty(spvDuty,req,resp);
        return CommonUtil.successJson("修改成功!");
    }

    @RequestMapping("updatetong")
    public JSONObject updateTongBaoByDid(SpvDuty spvDuty,HttpServletRequest req,HttpServletResponse resp){
        spvDutyService.updateTongBaoByDid(spvDuty);
        thymeleafSpvDuty(spvDuty,req,resp);
        return CommonUtil.successJson("发布成功!");
    }

    private void thymeleafSpvDuty(SpvDuty spvDuty, HttpServletRequest req, HttpServletResponse resp){
        ModelAndView modelAndView=new ModelAndView();
        if(spvDuty.getDid()!=0){
            SpvDuty spvDutyByDid = spvDutyService.findSpvDutyByDid(spvDuty.getDid());
            modelAndView.addObject("content",spvDutyByDid.getDutyContent());
            modelAndView.addObject("dutyDutyType",spvDutyByDid.getDutyTitle());
            modelAndView.addObject("time", DateUtil.upDate(spvDutyByDid.getNewTime()));
            modelAndView.addObject("name", spvDutyByDid.getSysStaff().getName());
        }else{
            String name = sysStaffService.findSysStaffByIdToName(spvDuty.getdCreateId());
            modelAndView.addObject("content",spvDuty.getDutyContent());
            modelAndView.addObject("dutyDutyType",spvDuty.getDutyTitle());
            modelAndView.addObject("time", DateUtil.getDate());
            modelAndView.addObject("name",name);
        }
        modelAndView.setViewName("jdzr/index1");
//        staticHtmlService.genHtmlPage(modelAndView,req,resp,spvDuty.getDutyTitle());
    }





}
