/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HomePageSeviceImpl
 * Author:   TSYH
 * Date:     2020-07-06 19:53
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.service.impl;

import com.xr.run.dao.*;
import com.xr.run.entity.*;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.entity.vo.RiskVo;
import com.xr.run.service.HomePageSevice;
import com.xr.run.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/*
 * @author TSYH
 * @create 2020-07-06
 * @since 1.0.0
 */
@Slf4j
@Service
public class HomePageSeviceImpl implements HomePageSevice {

    //静态页面生成地址
    private String destPath = "F:/tobacco/ms-mcms/html/2";

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SpvDutyMapper spvDutyMapper;

    @Autowired
    private RdWorkPlanMapper rdWorkPlanMapper;

    @Autowired
    private RdWorkDeploymentMapper rdWorkDeploymentMapper;

    @Autowired
    private RdHonestConversationMapper rdHonestConversationMapper;

    @Autowired
    private RdEntityResponsibilityMapper rdEntityResponsibilityMapper;

    //岗位风险
    @Autowired
    private PostriskcombingMapper postriskcombingMapper;

    //流程风险
    private ProcessrickMapper processrickMapper;


   //查询前五的责任监督
   public void loadDuty(Map map){
       Map<String, Object> mo = new HashMap<>();
       List<SpvDuty> spvDuties = spvDutyMapper.findSpvDutyTopFive();
       List<SpvDuty> list0 = new ArrayList<>();
       int i0 = 0;
       for (SpvDuty su : spvDuties) {
           //去除html标签
           String s = CommonUtil.delHTMLTag(su.getDutyContent());
           su.setDutyContent(s);
           list0.add(su);
           mo.put("DutyTitle", su.getDutyTitle()); //标题
           mo.put("DutyContent", su.getDutyContent()); //内容
           createIndexHtml(destPath + "/207/", "207/suList", (289 + i0) + ".html", mo);
           i0++;
       }
       map.put("supervise", list0);
       //return map;
   }

   //责任纪实
   public void loadRdWork(Map map){
       //工作计划
       List<RdWorkPlan> rdWorkPlans = rdWorkPlanMapper.findRdWorkPlanAll();
       //工作部署
       List<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentMapper.findRdWorkDeploymentAll();
       //廉政谈话
       List<RdHonestConversation> rdHonestConversations = rdHonestConversationMapper.findRdHonestConversationAll();
       //主体责任
       List<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityMapper.findRdEntityResponsibilityAll();

       List<RdWorkVo> list1 = new ArrayList<>();

       //将查询出来数据存在list1，按照时间降序进行排序
       //工作计划
       int i1 = 0;
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

           Map map1 = new HashMap();
           for (int i = 0; i < rdWorkPlans.size(); i++) {
               if(rdWorkVo.getId()==rdWorkPlans.get(i).getRdid()){
                   //下一页
                   if(i<rdWorkPlans.size()-1){
                       rdWorkVo.setNext(rdWorkPlans.get(i+1).getRdid());
                   }
                   //上一页
                   if(i>0){
                       rdWorkVo.setPrev(rdWorkPlans.get(i-1).getRdid());
                   }
               }
               //工作计划
               map1.put("rdWorkVo", rdWorkVo);
           }
           list1.add(rdWorkVo);
           createIndexHtml(destPath + "/185/0/", "185/rdList", rdWorkVo.getId() + ".html", map1);
           i1++;
       }


       int i2 = 0;
       //工作部署
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

           Map map1 = new HashMap();
           for (int i = 0; i < rdWorkDeployments.size(); i++) {
               if(rdWorkDeployment.getId()==rdWorkDeployments.get(i).getId()){
                   //下一页
                   if(i<rdWorkDeployments.size()-1){
                       rdWorkVo.setNext(rdWorkDeployments.get(i+1).getId());
                   }
                   //上一页
                   if(i>0){
                       rdWorkVo.setPrev(rdWorkDeployments.get(i-1).getId());
                   }
               }
               //工作部署
               map1.put("rdWorkVo", rdWorkVo);
           }
           list1.add(rdWorkVo);
           createIndexHtml(destPath + "/185/1/", "185/rdList", rdWorkVo.getId() + ".html", map1);
           i2++;
       }
       int i3 = 0;
       //廉政谈话
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

           Map map1 = new HashMap();
           for (int i = 0; i < rdHonestConversations.size(); i++) {
               if(rdHonestConversation.getId()==rdHonestConversations.get(i).getId()){
                   //下一页
                   if(i<rdHonestConversations.size()-1){
                       rdWorkVo.setNext(rdHonestConversations.get(i+1).getId());
                   }
                   //上一页
                   if(i>0){
                       rdWorkVo.setPrev(rdHonestConversations.get(i-1).getId());
                   }

               }
               //廉政谈话*/
               map1.put("rdWorkVo", rdWorkVo);
           }
           list1.add(rdWorkVo);
           createIndexHtml(destPath + "/185/2/", "185/rdList", rdWorkVo.getId() + ".html", map1);
           i3++;
       }
       int i4 = 0;
       //主体责任
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

           Map map1 = new HashMap();
           //分页问题
           for (int i = 0; i < rdEntityResponsibilities.size(); i++) {
               if(rdWorkVo.getId()==rdEntityResponsibilities.get(i).getId()){
                   //下一页
                   if(i<rdEntityResponsibilities.size()-1){
                       rdWorkVo.setNext(rdEntityResponsibilities.get(i+1).getId());
                   }
                   //上一页
                   if(i>0){
                       rdWorkVo.setPrev(rdEntityResponsibilities.get(i-1).getId());
                   }
               }
               //主体责任
               map1.put("rdWorkVo", rdWorkVo);
           }
           list1.add(rdWorkVo);
           createIndexHtml(destPath + "/185/3/", "185/rdList", rdWorkVo.getId() + ".html", map1);
           i4++;
       }

       listSort(list1);
       list1 = list1.subList(0, 5);
       map.put("rdWorkVos", list1);
   }


    public void loading() {
        Map<String, Object> map = new HashMap<>();

        //责任监督
        loadDuty(map);

        //查询前五的责任纪实
        loadRdWork(map);


        //风险防控

        //岗位风险
       /* List<Postriskcombing>  postriskCombingAll = postriskcombingMapper.findPostriskCombingAll();
        //流程风险
        List<Processrick>  processrickAll = processrickMapper.findProcessrickAll();

        List<RiskVo> riskVos = new ArrayList<>();

        for (Postriskcombing postriskcombing : postriskCombingAll) {
            RiskVo riskVo = new RiskVo();
            //postriskcombing.getPCreateTime();



        }

        for (Processrick processrick : processrickAll) {


        }*/








        createIndexHtml(destPath, "HomePage", "index.html", map);
    }




    //按日期排序（降序）
    private void listSort(List<RdWorkVo> list) {
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
                    log.info("日期排序出错：" + e);
                }
                return 0;
            }
        });
    }


    public void createIndexHtml(String path, String template, String child, Map map) {
        // 上下文
        Context context = new Context();
        context.setVariables(map);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 输出流
        //创建index.html页面，"rdList.html"
        File dest = new File(path, child);
        if (dest.exists()) {
            dest.delete();
        }
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            // 生成index.html
            templateEngine.process(template, context, writer);
        } catch (Exception e) {
            log.error("[静态页服务]：生成静态页异常", e);
        }
    }

    @Override
    public void getmoreHtml() {

    }

    @Override
    public void createDetailHtml() {

    }


}