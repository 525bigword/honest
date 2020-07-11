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
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.service.SpvDutyService;
import com.xr.run.util.CommonUtil;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.ArrayList;
import java.util.List;

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
    private RdWorkPlanMapper rdWorkPlanMapper;

    @Autowired
    private RdWorkDeploymentMapper rdWorkDeploymentMapper;

    @Autowired
    private RdHonestConversationMapper rdHonestConversationMapper;

    @Autowired
    private RdEntityResponsibilityMapper rdEntityResponsibilityMapper;




    //前端责任监督查询
    @RequestMapping("/dustList")
    public String dustList(String title, Integer pageNo, Integer pageSize){
        Page page = new Page(pageNo,pageSize);
        IPage<SpvDuty> supervise = spvDutyService.findSpvDuty(page,title);
        String jsonString = JSON.toJSONString(supervise);
        return jsonString;
    }

     //查询单个责任监督信息
    @RequestMapping("/getSuById")
    public String dustById(String id){
        if(id!=null&&!id.equals("null")&&!id.equals("")) {
            SpvDuty supervise = spvDutyService.findSpvDutyByDid(Integer.parseInt(id));
            String s = JSON.toJSONString(supervise);
            return s;
        }
        return null;
    }


    //工作计划



    //查询责任纪实所有的信息
    @RequestMapping("/rdWorkList")
    public String rdWorkList(String title, Integer pageNo, Integer pageSize) {
        List<RdWorkPlan> rdWorkPlans = rdWorkPlanMapper.findRdWorkPlanAll(title);
        //工作部署
        List<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentMapper.findRdWorkDeploymentAll(title);
        //廉政谈话
        List<RdHonestConversation> rdHonestConversations = rdHonestConversationMapper.findRdHonestConversationAll(title);
        //主体责任
        List<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityMapper.findRdEntityResponsibilityAll(title);
        List<RdWorkVo> list = new ArrayList<>();
        //记录总页码
        for (RdWorkPlan rdWorkPlan : rdWorkPlans) {
            //去除html标签
            String s = CommonUtil.delHTMLTag(rdWorkPlan.getContent());
            rdWorkPlan.setContent(s);
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkPlan.getContent());
            rdWorkVo.setTitle(rdWorkPlan.getTitle());
            rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
            rdWorkVo.setType(0);
            rdWorkVo.setId(rdWorkPlan.getRdid());
            list.add(rdWorkVo);
        }

        for (RdWorkDeployment rdWorkDeployment : rdWorkDeployments) {
            //去除html标签
            String s = CommonUtil.delHTMLTag(rdWorkDeployment.getContent());
            rdWorkDeployment.setContent(s);
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkDeployment.getContent());
            rdWorkVo.setTitle(rdWorkDeployment.getTitle());
            rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
            rdWorkVo.setType(1);
            rdWorkVo.setId(rdWorkDeployment.getId());
            list.add(rdWorkVo);
        }

        for (RdHonestConversation rdHonestConversation : rdHonestConversations) {
            //去除html标签
            String s = CommonUtil.delHTMLTag(rdHonestConversation.getContent());
            rdHonestConversation.setContent(s);
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdHonestConversation.getContent());
            rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
            rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
            rdWorkVo.setType(2);
            rdWorkVo.setId(rdHonestConversation.getId());
            list.add(rdWorkVo);
        }
        for (RdEntityResponsibility rdEntityResponsibility : rdEntityResponsibilities) {
            //去除html标签
            String s = CommonUtil.delHTMLTag(rdEntityResponsibility.getContent());
            rdEntityResponsibility.setContent(s);
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdEntityResponsibility.getContent());
            rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
            rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
            rdWorkVo.setType(3);
            rdWorkVo.setId(rdEntityResponsibility.getId());
            list.add(rdWorkVo);
        }
        //分页
        Integer count = list.size(); //记录总数
        Integer pageCount = 0; //页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; //开始索引
        int toIndex = 0; //结束索引

        if (pageNo > pageCount) {
            pageNo = pageCount;
        }
        if (!pageNo.equals(pageCount)) {
            fromIndex = (pageNo - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNo - 1) * pageSize;
            toIndex = count;
        }
        if (list.size()!=0) {
            List<RdWorkVo> pageList = list.subList(fromIndex, toIndex);
            String jsonString = JSON.toJSONString(pageList);
            String jso = "{\"total\":" + count + ",\"pages\":" + pageCount + ",\"rows\":" + jsonString + "}";
            return jso;
        } else {
            //没查找到返回
            return "null";
        }
    }


    @RequestMapping("/rdWorkDetailList")
    public String rdWorkDetailList(Integer type,String title,Integer pageNo, Integer pageSize){
        if(type==0){
            Page page = new Page(pageNo,pageSize);
            List<RdWorkVo> list = new ArrayList();
            IPage<RdWorkPlan> rdWorkPlanIndex1 = rdWorkPlanMapper.findRdWorkPlanIndex(page,title,2);
            for (RdWorkPlan rdWorkPlan : rdWorkPlanIndex1.getRecords()) {
                //去除html标签
                String s = CommonUtil.delHTMLTag(rdWorkPlan.getContent());
                rdWorkPlan.setContent(s);
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkPlan.getContent());
                rdWorkVo.setTitle(rdWorkPlan.getTitle());
                rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
                rdWorkVo.setType(0);
                rdWorkVo.setId(rdWorkPlan.getRdid());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdWorkPlanIndex1.getTotal() + ",\"pages\":" + rdWorkPlanIndex1.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }else if(type==1){
            Page page = new Page(pageNo,pageSize);
            IPage<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentMapper.findRdWorkDeploymentIndex(page,title,2);
            List<RdWorkVo> list = new ArrayList();
            for (RdWorkDeployment rdWorkDeployment : rdWorkDeployments.getRecords()) {
                //去除html标签
                String s = CommonUtil.delHTMLTag(rdWorkDeployment.getContent());
                rdWorkDeployment.setContent(s);
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdWorkDeployment.getContent());
                rdWorkVo.setTitle(rdWorkDeployment.getTitle());
                rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
                rdWorkVo.setType(1);
                rdWorkVo.setId(rdWorkDeployment.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdWorkDeployments.getTotal() + ",\"pages\":" + rdWorkDeployments.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }else if(type==2){
            Page page = new Page(pageNo,pageSize);
            IPage<RdHonestConversation> rdHonestConversations = rdHonestConversationMapper.findRdHonestConversationIndex(page,title);
            List<RdWorkVo> list = new ArrayList();
            for (RdHonestConversation rdHonestConversation : rdHonestConversations.getRecords()) {
                //去除html标签
                String s = CommonUtil.delHTMLTag(rdHonestConversation.getContent());
                rdHonestConversation.setContent(s);
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdHonestConversation.getContent());
                rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
                rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
                rdWorkVo.setType(2);
                rdWorkVo.setId(rdHonestConversation.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdHonestConversations.getTotal() + ",\"pages\":" + rdHonestConversations.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }else{
            Page page = new Page(pageNo,pageSize);
            IPage<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityMapper.findRdEntityResponsibilityIndex(page,title,2);
            List<RdWorkVo> list = new ArrayList();
            for (RdEntityResponsibility rdEntityResponsibility : rdEntityResponsibilities.getRecords()) {
                //去除html标签
                String s = CommonUtil.delHTMLTag(rdEntityResponsibility.getContent());
                rdEntityResponsibility.setContent(s);
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdEntityResponsibility.getContent());
                rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
                rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
                rdWorkVo.setType(3);
                rdWorkVo.setId(rdEntityResponsibility.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdEntityResponsibilities.getTotal() + ",\"pages\":" + rdEntityResponsibilities.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        }
    }
























}