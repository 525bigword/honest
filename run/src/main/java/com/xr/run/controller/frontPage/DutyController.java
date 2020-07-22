/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DutyController
 * Author:   TSYH
 * Date:     2020-07-10 15:01
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.RdEntityResponsibilityMapper;
import com.xr.run.dao.RdHonestConversationMapper;
import com.xr.run.dao.RdWorkDeploymentMapper;
import com.xr.run.dao.RdWorkPlanMapper;
import com.xr.run.entity.*;
import com.xr.run.entity.vo.CultureVo;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.service.*;
import com.xr.run.util.CommonUtil;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.*;

/**
 * 责任监督、责任纪实
 *
 * @author TSYH
 * @create 2020-07-10
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/duty")
public class DutyController {

    @Autowired
    private SpvDutyService spvDutyService;

    @Autowired
    private RdWorkPlanService rdWorkPlanService;

    @Autowired
    private RdWorkDeploymentService rdWorkDeploymentService;

    @Autowired
    private RdHonestConversationService rdHonestConversationService;

    @Autowired
    private RdEntityResponsibilityService rdEntityResponsibilityService;


    //前端责任监督查询
    @RequestMapping("/dustList")
    public String dustList(String title, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<SpvDuty> supervise = spvDutyService.findSpvDuty1(page, title);
        String jsonString = JSON.toJSONString(supervise);
        return jsonString;
    }

    //查询单个责任监督信息
    @RequestMapping("/getSuById")
    public String dustById(String id) {
        if (id != null && !id.equals("null") && !id.equals("")) {
            SpvDuty supervise = spvDutyService.findSpvDutyByDid(Integer.parseInt(id));
            String s = JSON.toJSONString(supervise);
            return s;
        }
        return "null";
    }


    @RequestMapping("/rdWorkDetailList")
    public String rdWorkDetailList(Integer type, String title, Integer pageNo, Integer pageSize) {
         if (type == 0) {
            Page page = new Page(pageNo, pageSize);
            List<RdWorkVo> list = new ArrayList();
            IPage<RdWorkPlan> rdWorkPlanIndex1 = rdWorkPlanService.findRdWorkPlanIndex(page, title, null);
            for (RdWorkPlan rdWorkPlan : rdWorkPlanIndex1.getRecords()) {
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkPlan.getContent());
                rdWorkVo.setTitle(rdWorkPlan.getTitle());
                rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
                rdWorkVo.setType(0);
                rdWorkVo.setName(rdWorkPlan.getName());
                rdWorkVo.setId(rdWorkPlan.getRdid());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdWorkPlanIndex1.getTotal() + ",\"pages\":" + rdWorkPlanIndex1.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        } else if (type == 1) {
            Page page = new Page(pageNo, pageSize);
            IPage<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentService.findRdWorkDeploymentIndex(page, title, null);
            List<RdWorkVo> list = new ArrayList();
            for (RdWorkDeployment rdWorkDeployment : rdWorkDeployments.getRecords()) {
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkDeployment.getContent());
                rdWorkVo.setTitle(rdWorkDeployment.getTitle());
                rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
                rdWorkVo.setType(1);
                rdWorkVo.setName(rdWorkDeployment.getName());
                rdWorkVo.setId(rdWorkDeployment.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdWorkDeployments.getTotal() + ",\"pages\":" + rdWorkDeployments.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }  else {
            Page page = new Page(pageNo, pageSize);
            IPage<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityService.findRdEntityResponsibilityIndex(page, title, null);
            List<RdWorkVo> list = new ArrayList();
            for (RdEntityResponsibility rdEntityResponsibility : rdEntityResponsibilities.getRecords()) {
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdEntityResponsibility.getContent());
                rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
                rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
                rdWorkVo.setType(3);
                rdWorkVo.setName(rdEntityResponsibility.getName());
                rdWorkVo.setId(rdEntityResponsibility.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdEntityResponsibilities.getTotal() + ",\"pages\":" + rdEntityResponsibilities.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }
    }

    @GetMapping("/getRdDutyByTyId")
    public String getRdDutyByTyId(String type, String id) {
         if (id != null && !id.equals("null") && !id.equals("")&&type != null && !type.equals("null") && !type.equals("")) {
             //根据id查询
            if (Integer.parseInt(type) == 0) {
                RdWorkPlan rdWorkPlan = rdWorkPlanService.findRdWorkPlanById(Integer.parseInt(id));
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkPlan.getContent());
                rdWorkVo.setTitle(rdWorkPlan.getTitle());
                rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
                rdWorkVo.setType(0);
                rdWorkVo.setName(rdWorkPlan.getName());
                rdWorkVo.setId(rdWorkPlan.getId());
                String s = JSON.toJSONString(rdWorkVo);
                return s;
            } else if (Integer.parseInt(type) == 1) {
                RdWorkDeployment rdWorkDeployment = rdWorkDeploymentService.findRdWorkDeployementById(Integer.parseInt(id));
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkDeployment.getContent());
                rdWorkVo.setTitle(rdWorkDeployment.getTitle());
                rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
                rdWorkVo.setType(1);
                rdWorkVo.setName(rdWorkDeployment.getName());
                rdWorkVo.setId(rdWorkDeployment.getId());
                String s = JSON.toJSONString(rdWorkVo);
                return s;
            } else if (Integer.parseInt(type) == 2) {
                RdHonestConversation rdHonestConversation = rdHonestConversationService.findRdHonestConversationById(Integer.parseInt(id));
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdHonestConversation.getContent());
                rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
                rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
                rdWorkVo.setType(2);
                rdWorkVo.setName(rdHonestConversation.getName());
                rdWorkVo.setId(rdHonestConversation.getId());
                String s = JSON.toJSONString(rdWorkVo);
                return s;
            } else {
                RdEntityResponsibility rdEntityResponsibility = rdEntityResponsibilityService.findRdEntityResponsibilityById(Integer.parseInt(id));
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdEntityResponsibility.getContent());
                rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
                rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
                rdWorkVo.setType(3);
                rdWorkVo.setName(rdEntityResponsibility.getName());
                rdWorkVo.setId(rdEntityResponsibility.getId());
                String s = JSON.toJSONString(rdWorkVo);
                return s;
            }
        }
        return "null";
    }





}