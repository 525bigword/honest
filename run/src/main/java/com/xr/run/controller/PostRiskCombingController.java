package com.xr.run.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.*;
import com.xr.run.service.*;
import com.xr.run.service.risk.PostRiskCombingService;
import com.xr.run.util.ResponseResult;
import com.xr.run.util.constants.Constants;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 12046
 * @create 2020/6/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/postRiskCombing")
public class PostRiskCombingController {
    @Value("${html.destPath}")
    private String destPath="";
    @Autowired
    private StaticHtmlService staticHtmlService;
    @Autowired
    private PostriskcombingService postriskcombingService;

    @Autowired
    private SysPostService sysPostService;

    @Autowired
    private SysMechanismService sysMechanismService;

    @Autowired
    private RiskpointassessmentService riskpointassessmentService;

    // 依赖注入业务类
    @Autowired
    private PostRiskCombingService postRiskCombingService;
    @RequestMapping("getList")
    @RequiresPermissions("postRiskCombing:list")
    public ResponseResult list(Integer pageNum, Integer pageSize) {
        //System.out.println(pageNum+"    "+pageSize);
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff loginStaff = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        // 分页查询
        Page page=new Page(pageNum,pageSize);
        IPage<Postriskcombing> resultPage = postRiskCombingService.list(page);
        //System.out.println(resultPage.getRecords());
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list",resultPage.getRecords());
        result.getInfo().put("total", resultPage.getTotal());
        result.getInfo().put("loginStaff", loginStaff);
        return result;
    }

    @RequestMapping("add")
    @PostMapping("postRiskCombing:add")
    public ResponseResult addPostRiskCombing(Postriskcombing postriskcombingOld,HttpServletRequest req, HttpServletResponse resp) {
        // 如果部门和岗位为空说明是公共风险
        // 添加数据
        postRiskCombingService.add(postriskcombingOld);
        Postriskcombing postRiskCombing=new Postriskcombing();//岗位风险
        Riskpointassessment riskPointAssessment=new Riskpointassessment();// 风险点统计
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff loginStaff = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        riskPointAssessment.setRiskCreateId(loginStaff.getSid());
        riskPointAssessment.setRiskCreateName(loginStaff.getName());
        //查询所有部门
        List<SysMechanism> smList=sysMechanismService.getAll();
        // 查询是否有未统计的部门
        List<SysMechanism> notUpdateList=riskpointassessmentService.notUpdatedDept(riskPointAssessment);
        if(notUpdateList.size()>0) {// 有未统计的则添加统计数据
            for (SysMechanism sm : notUpdateList) {
                // 统计风险等级数量
                postRiskCombing.setPDeptId(sm.getMid());
                postRiskCombing.setPGrade("一级风险");
                Integer num1 = postRiskCombingService.findByGrade(postRiskCombing);
                postRiskCombing.setPGrade("二级风险");
                Integer num2 = postRiskCombingService.findByGrade(postRiskCombing);
                postRiskCombing.setPGrade("三级风险");
                Integer num3 = postRiskCombingService.findByGrade(postRiskCombing);
                // 风险点赋值
                riskPointAssessment.setRiskImfomation(sm.getMid());
                riskPointAssessment.setNumberOneRisks(num1);
                riskPointAssessment.setNumberTwoRisks(num2);
                riskPointAssessment.setNumberThreeRisks(num3);
                // 添加统计数据
                //System.out.println(riskPointAssessment);
                riskpointassessmentService.add(riskPointAssessment);
            }
        }else{
            // 统计风险等级数量
            postRiskCombing.setPDeptId(postriskcombingOld.getPDeptId());
            System.out.println("修改统计表时postriskcombingOld的pdeptid:"+postriskcombingOld.getPDeptId());
            System.out.println("修改统计表时pdeptid:"+postRiskCombing.getPDeptId());
            postRiskCombing.setPGrade("一级风险");
            Integer num1 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPGrade("二级风险");
            Integer num2 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPGrade("三级风险");
            Integer num3 = postRiskCombingService.findByGrade(postRiskCombing);
            // 风险点赋值
            riskPointAssessment.setRiskImfomation(postRiskCombing.getPDeptId());
            riskPointAssessment.setNumberOneRisks(num1);
            riskPointAssessment.setNumberTwoRisks(num2);
            riskPointAssessment.setNumberThreeRisks(num3);
            //System.out.println("riskPointAssessment:"+riskPointAssessment);
            // 更新数据
            riskpointassessmentService.update(riskPointAssessment);
        }
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message", "添加成功");
        return result;
    }

    @RequestMapping("delete")
    @PostMapping("postRiskCombing:delete")
    public ResponseResult deleteById(String pcid){
        List<String> resultID= Arrays.asList(pcid.split(","));
        List<Integer> list=new ArrayList<>();
        for (String s: resultID){
            list.add(Integer.parseInt(s));
        }
        /*System.out.println(pcid);
        System.out.println(resultID);
        System.out.println(list);*/
        postRiskCombingService.deleteById(list);
        Postriskcombing postRiskCombing=new Postriskcombing();//岗位风险
        Riskpointassessment riskPointAssessment=new Riskpointassessment();// 风险点统计
        for (Integer deptid:list) {
            // 统计风险等级数量
            postRiskCombing.setPDeptId(deptid);
            postRiskCombing.setPGrade("一级风险");
            Integer num1 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPGrade("二级风险");
            Integer num2 = postRiskCombingService.findByGrade(postRiskCombing);
            postRiskCombing.setPGrade("三级风险");
            Integer num3 = postRiskCombingService.findByGrade(postRiskCombing);
            // 风险点赋值
            riskPointAssessment.setRiskImfomation(postRiskCombing.getPDeptId());
            riskPointAssessment.setNumberOneRisks(num1);
            riskPointAssessment.setNumberTwoRisks(num2);
            riskPointAssessment.setNumberThreeRisks(num3);
            //System.out.println("riskPointAssessment:"+riskPointAssessment);
            // 更新数据
            riskpointassessmentService.update(riskPointAssessment);
        }
        ResponseResult result = new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }

    @RequestMapping("update")
    @RequiresPermissions("postRiskCombing:update")
    public ResponseResult updatePostRiskCombing(Postriskcombing postriskcombing) {
        System.out.println(postriskcombing);
        ResponseResult result = new ResponseResult();
        if (postriskcombing != null) {
            postRiskCombingService.update(postriskcombing);
            result.getInfo().put("message", "修改成功");
        } else {
            result.getInfo().put("message", "修改失败");
        }
        return result;
    }


    @RequestMapping("findBy")
    @RequiresPermissions("postRiskCombing:list")
    public ResponseResult findBy( String pinfomationid,String pdeptid, String pgrade,Integer pageNum, Integer pageSize) {
        System.out.println(pinfomationid+"  "+pdeptid+"  ");
        ResponseResult responseResult = new ResponseResult();
        Integer infomationId = StringUtils.isEmpty(pinfomationid) ? 0:Integer.parseInt(pinfomationid);
        Integer deptId = StringUtils.isEmpty(pdeptid) ? 0:Integer.parseInt(pdeptid) ;
        // 分页查询
        Page page=new Page(pageNum,pageSize);
        IPage<Postriskcombing> resultPage = postRiskCombingService.getListBy(infomationId, deptId, pgrade,page);
        System.out.println("list:"+resultPage.getRecords());
        responseResult.getInfo().put("list", resultPage.getRecords());
        responseResult.getInfo().put("total", resultPage.getTotal());
        return responseResult;
    }

    @RequestMapping("getAllPost")
    public ResponseResult getAllSys_Post() {
        ResponseResult responseResult = new ResponseResult();
        List<SysPost> sysPostList = sysPostService.getSysPost();
        responseResult.getInfo().put("list", sysPostList);
        responseResult.getInfo().put("total", sysPostList.size());
        return responseResult;
    }

    @RequestMapping("getAllMechanism")
    public ResponseResult getAllMechanism() {
        ResponseResult responseResult = new ResponseResult();
        List<SysMechanism> list = sysMechanismService.findSysMechanismAll();
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
        return responseResult;
    }

    @RequestMapping("getAllMechanismByParent")
    public ResponseResult getAllMechanismByParent(Integer parent) {
        ResponseResult responseResult = new ResponseResult();
        List<SysMechanism> list = sysMechanismService.findSysMechanismByParentAll(parent);
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
        return responseResult;
    }


    @RequestMapping("getSysPostByMid")
    @RequiresPermissions("postRiskCombing:list")
    public ResponseResult getSysPostByMid(String pdeptid) {
        if(pdeptid.contains("=")) {
            pdeptid = pdeptid.split("=")[1];
        }
        System.out.println(pdeptid+"  pdeptid");
        ResponseResult responseResult = new ResponseResult();
        List<SysPost> list = sysPostService.getSysPostByMid(Integer.parseInt(pdeptid));
        responseResult.getInfo().put("list", list);
        return responseResult;
    }
    public void thymeleafPostriskcombing(Postriskcombing postriskcombing, HttpServletRequest req, HttpServletResponse resp){
        ModelAndView modelAndView=new ModelAndView();
        Postriskcombing postrisk = postriskcombingService.findByPid(postriskcombing.getPid());
        modelAndView.addObject("content",postrisk.getPRiskPointDescription());
        modelAndView.addObject("title",postrisk.getPProject());
        modelAndView.addObject("time", postrisk.getPCreateTime());
        modelAndView.addObject("name", postrisk.getPCreateName());
        modelAndView.setViewName("196/Info");
        staticHtmlService.genHtmlPage(destPath+"\\195\\196\\",modelAndView,req,resp,postriskcombing.getPid()+"");

    }

}
