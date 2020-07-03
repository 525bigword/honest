package com.xr.run.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.xr.run.entity.*;
import com.xr.run.service.PostriskcombingService;
import com.xr.run.service.RiskpointassessmentService;
import com.xr.run.service.SysMechanismService;
import com.xr.run.service.SysPostService;
import com.xr.run.util.ResponseResult;
import com.xr.run.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 12046
 * @create 2020/6/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/postRiskCombing")
public class PostRiskCombingController {

    @Autowired
    private PostriskcombingService postriskcombingService;

    @Autowired
    private SysPostService sysPostService;

    @Autowired
    private SysMechanismService sysMechanismService;

    @Autowired
    private RiskpointassessmentService riskpointassessmentService;


    @RequestMapping("getList")
    public ResponseResult list() {
        List<Postriskcombing> list = postriskcombingService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

    @RequestMapping("delete")
    public ResponseResult deletePostRiskCombing(String test) {
        List<Postriskcombing> postriskcombingList = JSON.parseArray(test, Postriskcombing.class);
        for (Postriskcombing postriskcombing : postriskcombingList) {
            postriskcombingService.deleteById(postriskcombing.getPid());
        }
        ResponseResult result = new ResponseResult();
        result.getInfo().put("message", "删除成功");
        return result;
    }

    @RequestMapping("update")
    public ResponseResult updatePostRiskCombing(Postriskcombing postriskcombing) {
        ResponseResult result = new ResponseResult();
        if (postriskcombing != null) {
            postriskcombingService.updateByPid(postriskcombing);
            result.getInfo().put("message", "修改成功");
        } else {
            result.getInfo().put("message", "修改失败");
        }
        return result;
    }

    @RequestMapping("add")
    public ResponseResult addPostRiskCombing(Postriskcombing postriskcombing) {
        ResponseResult result = new ResponseResult();
        if (postriskcombing == null) {
            result.getInfo().put("message", "需要添加的数据为NULL");
            return result;
        }

        String pGrade = "";
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff userInfo = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        //随机岗位风险编号
        postriskcombing.setPRiskId(RandomUtil.randomInt(100, 1999999999));
        postriskcombing.setPYear(DateUtil.date());
        postriskcombing.setPCreateTime(DateUtil.date());
        int pDeptId = postriskcombing.getPDeptId();
        Riskpointassessment riskpointassessment = riskpointassessmentService.getOneByRiskImfomation(pDeptId);
        Integer num = 0;
        //根据c值与L值计算风险等级
        String cValue = postriskcombing.getPCValue();
        String lValue = postriskcombing.getPProbableLValue();
        if (cValue.contains("大") && lValue.contains("大")) {
            pGrade = "一级风险";
            num = riskpointassessment.getNumberOneRisks() + 1;
        } else if (cValue.contains("小") && lValue.contains("小")) {
            pGrade = "三级风险";
            num = riskpointassessment.getNumberThreeRisks() + 1;
        } else {
            pGrade = "二级风险";
            num = riskpointassessment.getNumberTwoRisks() + 1;
        }
        postriskcombing.setPGrade(pGrade);
        //根据部门ID更新对应部门的风险汇总信息
        riskpointassessmentService.updateByRiskImfomation(pDeptId, pGrade, num);
        postriskcombing.setPDValue("D值");
        postriskcombing.setPCreateName(userInfo.getName());
        postriskcombing.setPCreateId(userInfo.getSid());
        postriskcombing.setPStatus(1);
        postriskcombingService.addPostriskcombing(postriskcombing);
        result.getInfo().put("message", "添加成功");
        return result;
    }


    @RequestMapping("findBy")
    public ResponseResult findBy(String pInfomationId, String pDeptId, String pGrade) {
        ResponseResult responseResult = new ResponseResult();
        Integer infomationId = pInfomationId != null ? Integer.parseInt(pInfomationId) : 0;
        Integer deptId = pDeptId != null ? Integer.parseInt(pDeptId) : 0;
        List<Postriskcombing> list = postriskcombingService.getListBy(infomationId, deptId, pGrade);
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
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
    public ResponseResult getAllMechanismByParent(String parent) {
        ResponseResult responseResult = new ResponseResult();
        List<SysMechanism> list = sysMechanismService.findSysMechanismByParentAll(Integer.parseInt(parent));
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
        return responseResult;
    }


    @RequestMapping("getSysPostByMid")
    public ResponseResult getSysPostByMid(String mid) {
        ResponseResult responseResult = new ResponseResult();
        List<SysPost> list = sysPostService.getSysPostByMid(Integer.parseInt(mid));
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
        return responseResult;
    }


}
