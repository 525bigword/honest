package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.*;
import com.xr.run.service.*;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("index")
public class IndexController {
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
    private DatacollectionService datacollectionService;
    /*廉政文化里的清风文苑查询数据*/
    @PostMapping("findWind")
    public JSONObject findWind(Integer pageNum, String wTitle,Integer pageRow){
        if(wTitle==null){
            wTitle="";
        }
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Wind> page=new Page(pageNum,pageRow);
        IPage<Wind> wind = windService.findWind(page, wTitle);
        return CommonUtil.successJson(wind);
    }
    /*廉政文化里的资料锦集查询数据*/
    @PostMapping("findDataConllection")
    public JSONObject findDataConllection(Integer pageNum,String dTitle, Integer pageRow){
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
    /*监督责任查询所有的监督数据 根据标题模糊查询*/
    @PostMapping("findSpvDuty")
    public JSONObject findSpvDuty( Integer pageNum, String dutyTitle,  Integer pageRow){
        if(dutyTitle==null){
            dutyTitle="";
        }
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        System.out.println("findSpvDuty");
        Page<SpvDuty> page=new Page(pageNum,pageRow);
        IPage<SpvDuty> findspv = spvDutyService.findSpvDuty(page,dutyTitle);
        return CommonUtil.successJson(findspv);
    }
    /*风险防控岗位风险梳理 根据风险点描述进行模糊查询 预防和控制措施做详情显示*/
    @PostMapping("findPostriskCombing")
    public JSONObject findPostriskCombing(Integer pageNum, String pRiskPointDescription, Integer pageRow){
        if(pRiskPointDescription==null){
            pRiskPointDescription="";
        }
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Postriskcombing> page=new Page(pageNum,pageRow);
        IPage<Postriskcombing> findspv =postriskcombingService.findPostriskCombingIndex(page,pRiskPointDescription);
        System.out.println("findPostriskCombing");
        return CommonUtil.successJson(findspv);
    }
    /*风险防控流程风险梳理 根据风险流程名称模糊查询 风险流程信息做显示详情*/
    @RequestMapping("findProcessrick")
    public JSONObject findProcessrick(Integer pageNum, String proName, Integer pageRow){
        if(proName==null){
            proName="";
        }
        System.out.println("findProcessrickIndex");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Processrick> page=new Page(pageNum,pageRow);
        IPage<Processrick> findwp =processrickService.findProcessrickIndex(page,proName);
        return CommonUtil.successJson(findwp);
    }

    /*责任纪实 工作计划根据标题模糊查询 内容做显示详情*/
    @RequestMapping("findRdWorkPlan")
    public JSONObject findRdWorkPlan(Integer pageNum, String title, Integer pageRow){
        if(title==null){
            title="";
        }
        System.out.println("findRdWorkPlan");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<RdWorkPlan> page=new Page(pageNum,pageRow);
        IPage<RdWorkPlan> findwp =rdWorkPlanService.findRdWorkPlanIndex(page,title);
        return CommonUtil.successJson(findwp);
    }

    /*责任纪实 工作部署 根据标题模糊查询 内容做显示详情*/
    @RequestMapping("findRdWorkDeployment")
    public JSONObject findRdWorkDeploymentIndex(Integer pageNum, String title, Integer pageRow){
        if(title==null){
            title="";
        }
        System.out.println("findRdWorkDeployment");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<RdWorkDeployment> page=new Page(pageNum,pageRow);
        IPage<RdWorkDeployment> findwp =rdWorkDeploymentService.findRdWorkDeploymentIndex(page,title);
        return CommonUtil.successJson(findwp);
    }
    /*责任纪实 主体责任 根据标题模糊查询 内容做显示详情*/
    @RequestMapping("findRdEntityResponsibility")
    public JSONObject findRdEntityResponsibilityIndex(Integer pageNum, String title, Integer pageRow){
        if(title==null){
            title="";
        }
        System.out.println("findRdEntityResponsibility");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<RdEntityResponsibility> page=new Page(pageNum,pageRow);
        IPage<RdEntityResponsibility> findwp =rdEntityResponsibilityService.findRdEntityResponsibilityIndex(page,title);
        return CommonUtil.successJson(findwp);
    }
    /*纪检报表 根据文件名模糊查询*/
    @RequestMapping("findDcpReport")
    public JSONObject findDcpReportIndex(Integer pageNum, String report, Integer pageRow){
        if(report==null){
            report="";
        }
        System.out.println("findDcpReportIndex");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<DcpReport> page=new Page(pageNum,pageRow);
        IPage<DcpReport> findwp =dcpReportService.findDcpReportIndex(page,report);
        return CommonUtil.successJson(findwp);
    }
}
