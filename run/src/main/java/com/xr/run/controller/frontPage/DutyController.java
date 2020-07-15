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
        IPage<SpvDuty> supervise = spvDutyService.findSpvDuty(page, title);
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

    //查询责任纪实所有的信息
    @RequestMapping("/rdWorkList")
    public String rdWorkList(String title, Integer pageNo, Integer pageSize) {
        List<RdWorkPlan> rdWorkPlans = rdWorkPlanService.findRdWorkPlanAll(title);
        //工作部署
        List<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentService.findRdWorkDeploymentAll(title);
        //廉政谈话
        List<RdHonestConversation> rdHonestConversations = rdHonestConversationService.findRdHonestConversationAll(title);
        //主体责任
        List<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityService.findRdEntityResponsibilityAll(title);
        List<RdWorkVo> list = new ArrayList<>();
        //记录总页码
        for (RdWorkPlan rdWorkPlan : rdWorkPlans) {
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkPlan.getContent());
            rdWorkVo.setTitle(rdWorkPlan.getTitle());
            rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
            rdWorkVo.setType(0);
            rdWorkVo.setName(rdWorkPlan.getName());
            rdWorkVo.setId(rdWorkPlan.getRdid());
            list.add(rdWorkVo);
        }

        for (RdWorkDeployment rdWorkDeployment : rdWorkDeployments) {
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkDeployment.getContent());
            rdWorkVo.setTitle(rdWorkDeployment.getTitle());
            rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
            rdWorkVo.setType(1);
            rdWorkVo.setName(rdWorkDeployment.getName());
            rdWorkVo.setId(rdWorkDeployment.getId());
            list.add(rdWorkVo);
        }

        for (RdHonestConversation rdHonestConversation : rdHonestConversations) {
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdHonestConversation.getContent());
            rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
            rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
            rdWorkVo.setType(2);
            rdWorkVo.setName(rdHonestConversation.getName());
            rdWorkVo.setId(rdHonestConversation.getId());
            list.add(rdWorkVo);
        }
        for (RdEntityResponsibility rdEntityResponsibility : rdEntityResponsibilities) {
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdEntityResponsibility.getContent());
            rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
            rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
            rdWorkVo.setName(rdEntityResponsibility.getName());
            rdWorkVo.setType(3);
            rdWorkVo.setId(rdEntityResponsibility.getId());
            list.add(rdWorkVo);
        }
        //排序
        rdWorkVoSort(list);
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
        if (list.size() != 0) {
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
    public String rdWorkDetailList(Integer type, String title, Integer pageNo, Integer pageSize) {
         if (type == 0) {
            Page page = new Page(pageNo, pageSize);
            List<RdWorkVo> list = new ArrayList();
            IPage<RdWorkPlan> rdWorkPlanIndex1 = rdWorkPlanService.findRdWorkPlanIndex(page, title, 2);
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
            IPage<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentService.findRdWorkDeploymentIndex(page, title, 2);
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
        } else if (type == 2) {
            Page page = new Page(pageNo, pageSize);
            IPage<RdHonestConversation> rdHonestConversations = rdHonestConversationService.findRdHonestConversationIndex(page, title);
            List<RdWorkVo> list = new ArrayList();
            for (RdHonestConversation rdHonestConversation : rdHonestConversations.getRecords()) {
                RdWorkVo rdWorkVo = new RdWorkVo();
                rdWorkVo.setContent(rdHonestConversation.getContent());
                rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
                rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
                rdWorkVo.setType(2);
                rdWorkVo.setName(rdHonestConversation.getName());
                rdWorkVo.setId(rdHonestConversation.getId());
                list.add(rdWorkVo);
            }
            String jsonString = JSON.toJSONString(list);
            String jso = "{\"total\":" + rdHonestConversations.getTotal() + ",\"pages\":" + rdHonestConversations.getPages() + ",\"rows\":" + jsonString + "}";
            return jso;
        } else {
            Page page = new Page(pageNo, pageSize);
            IPage<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityService.findRdEntityResponsibilityIndex(page, title, 2);
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


    private void rdWorkVoSort(List<RdWorkVo> list) {
        //Collections的sort方法默认是升序排列，如果需要降序排列时就需要重写compare方法
        Collections.sort(list, new Comparator<RdWorkVo>() {
            @Override
            public int compare(RdWorkVo o1, RdWorkVo o2) {
                try {
                    //获取体检日期，并把其类型由String转成Date，便于比较。
                    Date dt1 = o1.getCreateTime();
                    Date dt2 = o2.getCreateTime();
                    //以下代码决定按日期降序排序，若将return“-1”与“1”互换，即可实现升序。
                    //getTime 方法返回一个整数值，这个整数代表了从 1970 年 1 月 1 日开始计算到 Date 对象中的时间之间的毫秒数。
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }

                } catch (Exception e) {
                }
                return 0;
            }
        });
    }


}