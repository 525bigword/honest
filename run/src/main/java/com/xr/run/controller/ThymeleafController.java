package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.*;
import com.xr.run.entity.daily.LetterReport;
import com.xr.run.service.*;
import com.xr.run.service.daily.LetterReportService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value="/th")
public class ThymeleafController {
    @Autowired
    private StaticHtmlService staticHtmlService;
    @Autowired
    private DatacollectionService datacollectionService;
    @Autowired
    private WindService windService;
    @Autowired
    private RdWorkPlanService rdWorkPlanService;
    @Autowired
    private ProcessrickService processrickService;
    @Autowired
    private SpvDutyService spvDutyService;
    @Autowired
    private RdWorkDeploymentService rdWorkDeploymentService;
    @Autowired
    private DcpReportService dcpReportService;
    @Autowired
    private RdEntityResponsibilityService rdEntityResponsibilityService;
    @Autowired
    private PostriskcombingService postriskcombingService;
    @Autowired
    private LetterReportService letterreportService;
    @RequestMapping("/produce")
    public void index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Page page=new Page(1,10);
        IPage<Datacollection> dataConllection = datacollectionService.findDataConllection(page, "");
        for (Datacollection record : dataConllection.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("dFileName",record.getDFileName());
            modelAndView.addObject("dPdf",record.getDPdf());
            modelAndView.addObject("dFile",record.getDFile());
            modelAndView.addObject("title",record.getDTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getDCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("index1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getDTitle());
            modelAndView=null;
        }
        IPage<Wind> wind = windService.findWind(page, "");
        for (Wind record : wind.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("content",record.getWContent());
            modelAndView.addObject("title",record.getWTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getWCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("wind1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getWTitle());
            modelAndView=null;
        }
        //工作计划
        IPage<RdWorkPlan> findwp =rdWorkPlanService.findRdWorkPlanIndex(page,"");
        for (RdWorkPlan record : findwp.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("content",record.getContent());
            modelAndView.addObject("title",record.getTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("zrjs/index1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getTitle());
            modelAndView=null;
        }
        //主题责任
        IPage<RdEntityResponsibility> rdEntityResponsibilityIndex = rdEntityResponsibilityService.findRdEntityResponsibilityIndex(page, "");
        for (RdEntityResponsibility record : rdEntityResponsibilityIndex.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("content",record.getContent());
            modelAndView.addObject("title",record.getTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("zrjs/rdentityresponsibility1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getTitle());
            modelAndView=null;
        }
        //工作计划
        IPage<RdWorkDeployment> rdWorkDeploymentIndex = rdWorkDeploymentService.findRdWorkDeploymentIndex(page, "");
        for (RdWorkDeployment record : rdWorkDeploymentIndex.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("content",record.getContent());
            modelAndView.addObject("title",record.getTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("zrjs/rdworkdeployment1.html");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getTitle());
            modelAndView=null;
        }
        //TODO 纪检报表
        IPage<DcpReport> dcpReportIndex = dcpReportService.findDcpReportIndex(page, "");
        for (DcpReport record : dcpReportIndex.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("reportType",record.getReportType());
            modelAndView.addObject("url",record.getUrl());
            modelAndView.addObject("report",record.getReport());
            modelAndView.addObject("time", DateUtil.upDate(record.getNewTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("jjbb/index1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getReportType());
            modelAndView=null;
        }
        //监督责任
        IPage<SpvDuty> spvDuty = spvDutyService.findSpvDuty(page, "");
        for (SpvDuty record : spvDuty.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("content",record.getDutyContent());
            modelAndView.addObject("dutyDutyType",record.getDutyTitle());
            modelAndView.addObject("time", DateUtil.upDate(record.getNewTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("jdzr/index1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getDutyTitle());
            modelAndView=null;
        }
        //岗位风险
        IPage<Postriskcombing> postriskCombingIndex = postriskcombingService.findPostriskCombingIndex(page, "");
        for (Postriskcombing record : postriskCombingIndex.getRecords()) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("pProject",record.getPProject());
            modelAndView.addObject("pRiskPointDescription",record.getPRiskPointDescription());
            modelAndView.addObject("time", DateUtil.upDate(record.getPCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("fxfk/index1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getPProject());
            modelAndView=null;
        }
        //流程风险
        List<Processrick> findps =processrickService.findProcessrickIndex(page,"");
        for (Processrick record : findps) {
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("proName",record.getProName());
            modelAndView.addObject("proInfomation",record.getProInfomation());
            modelAndView.addObject("time", DateUtil.upDate(record.getProCreateTime()));
            modelAndView.addObject("name", record.getSysStaff().getName());
            modelAndView.setViewName("fxfk/processrick1");
            staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse,record.getProName());
            modelAndView=null;
        }
//        return modelAndView;
    }
    @RequestMapping("datacollection")
    public ModelAndView indexa(){
        ModelAndView modelAndView=new ModelAndView( );
        Page page=new Page(1,10);
        IPage<Datacollection> dataConllection = datacollectionService.findDataConllection(page, "");
//        IPage<Wind> wind = windService.findWind(page, "");
        modelAndView.addObject("list",dataConllection.getRecords());
        modelAndView.addObject("total",dataConllection.getTotal());
        modelAndView.setViewName("index");
        return modelAndView;
    }
    /*廉政文化里的清风文苑查询数据*/
    @RequestMapping("wind")
    public ModelAndView findWind(){
        ModelAndView modelAndView=new ModelAndView();
        Page<Wind> page=new Page(1,10);
        IPage<Wind> wind = windService.findWind(page, "");
        modelAndView.addObject("list",wind.getRecords());
        modelAndView.setViewName("wind");
        return modelAndView;
    }
    /*责任纪实 工作计划根据标题模糊查询 内容做显示详情*/
    @RequestMapping("rdworkplan")
    public ModelAndView findRdWorkPlan(){
        ModelAndView modelAndView=new ModelAndView();
        Page<RdWorkPlan> page=new Page(1,10);
        IPage<RdWorkPlan> findwp =rdWorkPlanService.findRdWorkPlanIndex(page,"");
        modelAndView.addObject("list",findwp.getRecords());
        modelAndView.setViewName("zrjs/index");
        return modelAndView;
    }
    /*责任纪实 工作部署 根据标题模糊查询 内容做显示详情*/
    @RequestMapping("rdworkdeployment")
    public ModelAndView findRdWorkDeploymentIndex(){
        ModelAndView modelAndView=new ModelAndView();
        Page<RdWorkDeployment> page=new Page(1,10);
        IPage<RdWorkDeployment> findrdwd =rdWorkDeploymentService.findRdWorkDeploymentIndex(page,"");
        modelAndView.addObject("list",findrdwd.getRecords());
        modelAndView.setViewName("zrjs/rdworkdeployment");
        return modelAndView;
    }
    /*责任纪实 主体责任 根据标题模糊查询 内容做显示详情*/
    @RequestMapping("rdentityresponsibility")
    public ModelAndView findRdEntityResponsibilityIndex(){
        ModelAndView modelAndView=new ModelAndView();
        Page<RdEntityResponsibility> page=new Page(1,10);
        IPage<RdEntityResponsibility> findwp =rdEntityResponsibilityService.findRdEntityResponsibilityIndex(page,"");
        modelAndView.addObject("list",findwp.getRecords());
        modelAndView.setViewName("zrjs/rdentityresponsibility");
        return modelAndView;
    }
    /*纪检报表 根据文件名模糊查询*/
    @RequestMapping("dcpreport")
    public ModelAndView findDcpReportIndex(){
        ModelAndView modelAndView=new ModelAndView();
        Page<DcpReport> page=new Page(1,10);
        IPage<DcpReport> findwp =dcpReportService.findDcpReportIndex(page,"");
        List<DcpReport> records = findwp.getRecords();
        for (DcpReport record : records) {
            String substring = record.getReport().substring(0, record.getReport().lastIndexOf("."));
            record.setReport(substring);
        }
        modelAndView.addObject("list",records);
        modelAndView.setViewName("jjbb/index");
        return modelAndView;
    }
    /*风险防控岗位风险梳理 根据风险点描述进行模糊查询 预防和控制措施做详情显示*/
    @RequestMapping("postriskcombing")
    public ModelAndView findPostriskCombing(){
        ModelAndView modelAndView=new ModelAndView();
        Page<Postriskcombing> page=new Page(1,10);
        IPage<Postriskcombing> findspv =postriskcombingService.findPostriskCombingIndex(page,"");
        modelAndView.addObject("list",findspv.getRecords());
        modelAndView.setViewName("fxfk/index");
        return modelAndView;
    }
    /*风险防控流程风险梳理 根据风险流程名称模糊查询 风险流程信息做显示详情*/
    @RequestMapping("processrick")
    public ModelAndView findProcessrick(){
        ModelAndView modelAndView=new ModelAndView();
        Page<Processrick> page=new Page(1,10);
        List<Processrick> findwp =processrickService.findProcessrickIndex(page,"");
        modelAndView.addObject("list",findwp);
        modelAndView.setViewName("fxfk/processrick");
        return modelAndView;
    }

    /*监督责任查询所有的监督数据 根据标题模糊查询*/
    @RequestMapping("spvduty")
    public ModelAndView findSpvDuty(){
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("findSpvDuty");
        Page<SpvDuty> page=new Page(1,10);
        IPage<SpvDuty> findspv = spvDutyService.findSpvDuty(page,"");
        modelAndView.addObject("list",findspv.getRecords());
        modelAndView.setViewName("jdzr/index");
        return modelAndView;
    }
    /*信访举报 填写受理登记表*/
    @RequestMapping("letterreporter")
    public ModelAndView addLetter(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("letterid", "XF"+UUID.randomUUID().toString());
        modelAndView.setViewName("xfjb/letterRepoter");
        return modelAndView;

    }
    @RequestMapping("addletter")
    public ModelAndView addletter(LetterReport letterReport){
        letterreportService.addletter(letterReport);
        ModelAndView modelAndView=new ModelAndView();
      //  modelAndView.addObject("message", "举报成功");
        modelAndView.setViewName("xfjb/letterRepoter");
      return modelAndView;
    }
}
