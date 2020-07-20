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

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.RunApplication;
import com.xr.run.dao.*;
import com.xr.run.dao.tam.SmokestyleMapper;
import com.xr.run.entity.*;
import com.xr.run.entity.tam.Smokestyle;
import com.xr.run.entity.vo.CultureVo;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.entity.vo.RiskVo;
import com.xr.run.service.HomePageSevice;
import com.xr.run.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/*
 * 动态生成静态页面
 * @author TSYH
 * @create 2020-07-06
 * @since 1.0.0
 */
@Slf4j
@Service
public class HomePageSeviceImpl implements HomePageSevice {

    //静态页面生成地址${html.destPath}
    @Value("${html.destPath}")
    private String destPath;

    public void a() {
        System.out.println(destPath);
    }

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
    @Autowired
    private ProcessrickMapper processrickMapper;

    //廉政教育
    @Autowired
    private EducationPoliticsMapper educationPoliticsMapper;

    //纪检报表
    @Autowired
    private DcpReportMapper dcpReportMapper;

    //资料集锦
    @Autowired
    private DatacollectionMapper datacollectionMapper;

    //清风文苑
    @Autowired
    private WindMapper windMapper;

    //通知公告
    @Autowired
    private SystemMessageMapper systemMessageMapper;

    //潭烟风貌
    @Autowired
    private SmokestyleMapper smokestyleMapper;

    //查询前五的责任监督
    public void loadDuty(Map map) {
        Map<String, Object> mo = new HashMap<>();
        List<SpvDuty> spvDuties = spvDutyMapper.findSpvDutyTopFive();
        List<SpvDuty> list0 = new ArrayList<>();
        int i0 = 0;
        for (SpvDuty su : spvDuties) {
            //去除html标签
            list0.add(su);
            mo.put("dutyTitle", su.getDutyTitle()); //标题
            mo.put("dutyContent", su.getDutyContent()); //内容
            mo.put("newTime", su.getNewTime());
            mo.put("name", su.getName());
            mo.put("dutyType", su.getDutyType());
            createIndexHtml(destPath + "/207/", "207/suList", (289 + i0) + ".html", mo);
            i0++;
        }
        if (list0.size() == 0) {
            //map.put("duContent", "");
            SpvDuty spvDuty = new SpvDuty();
            spvDuty.setDutyContent("");
            list0.add(spvDuty);
        } else {
            String s = CommonUtil.delHTMLTag(list0.get(0).getDutyContent());//去除标签
            map.put("duContent", s);
        }
        map.put("supervise", list0);
    }

    //责任纪实
    public void loadRdWork(Map map) {
        //工作计划
        List<RdWorkPlan> rdWorkPlans = rdWorkPlanMapper.findRdWorkPlanAll("");
        //工作部署
        List<RdWorkDeployment> rdWorkDeployments = rdWorkDeploymentMapper.findRdWorkDeploymentAll("");
        //廉政谈话
        List<RdHonestConversation> rdHonestConversations = rdHonestConversationMapper.findRdHonestConversationAll("");
        //主体责任
        List<RdEntityResponsibility> rdEntityResponsibilities = rdEntityResponsibilityMapper.findRdEntityResponsibilityAll("");

        List<RdWorkVo> list1 = new ArrayList<>();

        //将查询出来数据存在list1，按照时间降序进行排序
        //工作计划
        int i0 = 0;
        for (RdWorkPlan rdWorkPlan : rdWorkPlans) {
            //去除html标签
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkPlan.getContent());
            rdWorkVo.setTitle(rdWorkPlan.getTitle());
            rdWorkVo.setCreateTime(rdWorkPlan.getCreateTime());
            rdWorkVo.setType(0);
            rdWorkVo.setId(rdWorkPlan.getRdid());
            rdWorkVo.setName(rdWorkPlan.getName());
            list1.add(rdWorkVo);
            Map map1 = new HashMap();
            map1.put("rdWorkVo", rdWorkVo);
            if (i0 == 0) {
                createIndexHtml(destPath + "/185/0/", "185/rdList", rdWorkVo.getId() + ".html", map1);
            }
            i0++;
        }


        //工作部署
        int i1 = 0;
        for (RdWorkDeployment rdWorkDeployment : rdWorkDeployments) {
            //去除html标签
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdWorkDeployment.getContent());
            rdWorkVo.setTitle(rdWorkDeployment.getTitle());
            rdWorkVo.setCreateTime(rdWorkDeployment.getCreateTime());
            rdWorkVo.setType(1);
            rdWorkVo.setId(rdWorkDeployment.getId());
            rdWorkVo.setName(rdWorkDeployment.getName());
            list1.add(rdWorkVo);
            Map map1 = new HashMap();
            map1.put("rdWorkVo", rdWorkVo);
            if (i1 == 0) {
                createIndexHtml(destPath + "/185/1/", "185/rdList", rdWorkVo.getId() + ".html", map1);
            }
            i1++;
        }
        //廉政谈话
        int i2 = 0;
        for (RdHonestConversation rdHonestConversation : rdHonestConversations) {
            //去除html标签
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdHonestConversation.getContent());
            rdWorkVo.setTitle(rdHonestConversation.getSyllabus());
            rdWorkVo.setCreateTime(rdHonestConversation.getCreateTime());
            rdWorkVo.setType(2);
            rdWorkVo.setId(rdHonestConversation.getId());
            rdWorkVo.setName(rdHonestConversation.getName());
            list1.add(rdWorkVo);
            Map map1 = new HashMap();
            map1.put("rdWorkVo", rdWorkVo);
            if (i2 == 0) {
                createIndexHtml(destPath + "/185/2/", "185/rdList", rdWorkVo.getId() + ".html", map1);
            }
            i2++;
        }
        //主体责任
        int i3 = 0;
        for (RdEntityResponsibility rdEntityResponsibility : rdEntityResponsibilities) {
            //去除html标签
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent(rdEntityResponsibility.getContent());
            rdWorkVo.setTitle(rdEntityResponsibility.getTitle());
            rdWorkVo.setCreateTime(rdEntityResponsibility.getCreateTime());
            rdWorkVo.setType(3);
            rdWorkVo.setId(rdEntityResponsibility.getId());
            rdWorkVo.setName(rdEntityResponsibility.getName());
            list1.add(rdWorkVo);
            Map map1 = new HashMap();
            map1.put("rdWorkVo", rdWorkVo);
            if (i3 == 0) {
                createIndexHtml(destPath + "/185/3/", "185/rdList", rdWorkVo.getId() + ".html", map1);
            }
            i3++;
        }
        listSort(list1);
        log.info("====================list1========list1==================================list1list1list1==="+list1);

        if (list1.size() != 0) {

            String s = CommonUtil.delHTMLTag(list1.get(0).getContent());//去除标签
            map.put("rdContent", s);
            if (list1.size() > 0 && list1.size() < 6) {
                list1 = list1.subList(0, list1.size());
            } else {
                list1 = list1.subList(0, 6);
            }
            for (RdWorkVo rdWorkVo : list1) {
                Map map1 = new HashMap();
                map1.put("rdWorkVo", rdWorkVo);
                if (rdWorkVo.getType() == 0) {
                    createIndexHtml(destPath + "/185/0/", "185/rdList", rdWorkVo.getId() + ".html", map1);
                } else if (rdWorkVo.getType() == 1) {
                    createIndexHtml(destPath + "/185/1/", "185/rdList", rdWorkVo.getId() + ".html", map1);
                } else if (rdWorkVo.getType() == 2) {
                    createIndexHtml(destPath + "/185/2/", "185/rdList", rdWorkVo.getId() + ".html", map1);
                } else {
                    createIndexHtml(destPath + "/185/3/", "185/rdList", rdWorkVo.getId() + ".html", map1);
                }
            }

        } else {
            RdWorkVo rdWorkVo = new RdWorkVo();
            rdWorkVo.setContent("");
            //map.put("rdContent", "");
            list1.add(rdWorkVo);
        }
        map.put("rdWorkVos", list1);
    }


    //获得更多责任监督
    private void getMoreDuty() {
        createIndexHtml(destPath + "/207/", "207/index", "index.html", null);
    }

    private void getMoreRdWork() {
        createIndexHtml(destPath + "/185/", "185/index", "index.html", null);
    }


    //生成不同的RdWork
    private void getDifferentRdWord() {
        //工作计划
        Map map0 = new HashMap();
        map0.put("type", 0);
        createIndexHtml(destPath + "/185/0/", "185/indexIn", "index.html", map0);
        //工作部署
        Map map1 = new HashMap();
        map1.put("type", 1);
        createIndexHtml(destPath + "/185/1/", "185/indexIn", "index.html", map1);
        //廉政谈话
        Map map2 = new HashMap();
        map2.put("type", 2);
        createIndexHtml(destPath + "/185/2/", "185/indexIn", "index.html", map2);
        //主体责任
        Map map3 = new HashMap();
        map3.put("type", 3);
        createIndexHtml(destPath + "/185/3/", "185/indexIn", "index.html", map3);
    }


    //廉政教育
    private void loadEducation(Map map) {
        Map map0 = new HashMap();
        //查找
        EducationPolitics allEducation = educationPoliticsMapper.findAllEducationsByType("廉政要闻");
        map0.put("education", allEducation);
        createIndexHtml(destPath + "/181/0/", "181/eduList", allEducation.getId() + ".html", map0);

        Map map1 = new HashMap();
        //查找
        EducationPolitics allEducation1 = educationPoliticsMapper.findAllEducationsByType("文件制度");
        map1.put("education", allEducation1);
        createIndexHtml(destPath + "/181/1/", "181/eduList", allEducation1.getId() + ".html", map1);

        Map map2 = new HashMap();
        //查找
        EducationPolitics allEducation2 = educationPoliticsMapper.findAllEducationsByType("领导讲话");
        map2.put("education", allEducation2);
        createIndexHtml(destPath + "/181/2/", "181/eduList", allEducation2.getId() + ".html", map2);

        Map map3 = new HashMap();
        //查找
        EducationPolitics allEducation3 = educationPoliticsMapper.findAllEducationsByType("警钟长鸣");
        map3.put("education", allEducation3);
        createIndexHtml(destPath + "/181/3/", "181/eduList", allEducation3.getId() + ".html", map3);

        List<EducationPolitics> educationPolitics = educationPoliticsMapper.findAllEducationsTopNine();

        for (EducationPolitics education : educationPolitics) {
            Map map4 = new HashMap();
            map4.put("education", education);
            if (education.getType().equals("廉政要闻")) {
                education.setNext(0);
                createIndexHtml(destPath + "/181/0/", "181/eduList", education.getId() + ".html", map4);
            } else if (education.getType().equals("文件制度")) {
                education.setNext(1);
                createIndexHtml(destPath + "/181/1/", "181/eduList", education.getId() + ".html", map4);
            } else if (education.getType().equals("领导讲话")) {
                education.setNext(2);
                createIndexHtml(destPath + "/181/2/", "181/eduList", education.getId() + ".html", map4);
            } else {
                education.setNext(3);
                createIndexHtml(destPath + "/181/3/", "181/eduList", education.getId() + ".html", map4);
            }
        }
        if (educationPolitics.size() == 0) {
            EducationPolitics e = new EducationPolitics();
            e.setContent("");
        }
        map.put("educations", educationPolitics);
    }

    //更多廉政教育
    private void getMoreEducation() {
        createIndexHtml(destPath + "/181/", "181/index", "index.html", null);
    }

    //生成不同的廉政教育
    private void getDifferentEducation() {
        //廉政要闻
        Map map0 = new HashMap();
        map0.put("type", 0);
        createIndexHtml(destPath + "/181/0/", "181/indexIn", "index.html", map0);
        //文件制度
        Map map1 = new HashMap();
        map1.put("type", 1);
        createIndexHtml(destPath + "/181/1/", "181/indexIn", "index.html", map1);
        //领导讲话
        Map map2 = new HashMap();
        map2.put("type", 2);
        createIndexHtml(destPath + "/181/2/", "181/indexIn", "index.html", map2);
        //警钟长鸣
        Map map3 = new HashMap();
        map3.put("type", 3);
        createIndexHtml(destPath + "/181/3/", "181/indexIn", "index.html", map3);
    }



    public void loading() {

        Map<String, Object> map = new HashMap<>();
        //TODO 责任监督
        loadDuty(map);
        //责任监督更多
        getMoreDuty();
        //TODO 责任纪实
        loadRdWork(map);
        //责任纪实更多
        getMoreRdWork();
        //TODO 廉政文化
        loadCulture(map);
        //廉政文化更多
        getMoreCultrue();
        //不同的廉政文化
        getDifferentCultrue();

        //TODO 风险防控
        loadRisk(map);
        //风险防控更多
        getMoreRisk();
        //生成不同的风险防控
        getDifferentRisk();

        //生成不同的RdWork
        getDifferentRdWord();

        //TODO 廉政教育
        loadEducation(map);
        //廉政教育更多
        getMoreEducation();
        //获得不同的廉政教育
        getDifferentEducation();


        //TODO 通知公告
        loadSystemMsg(map);

        //TODO 纪检报表
        loadReport(map);
        //纪检报表更多
        getMoreReport();

        //TODO 潭烟风貌
        loadTam(map);
        //更多潭烟风貌
        getMoreTam();


        createIndexHtml(destPath, "HomePage", "index.html", map);
    }


    //潭烟风貌
    private void loadTam(Map map) {
        List<Smokestyle> allSmokestyle = smokestyleMapper.findAllSmokestyle2();
        List<Smokestyle> list = new ArrayList<>();
        if(allSmokestyle.size()==0){
            Smokestyle smokestyle = new Smokestyle();
            smokestyle.setSmocontent("");
            list.add(smokestyle);
        }
        for (Smokestyle smokestyle : allSmokestyle) {
            HashMap map1 = new HashMap();
            map1.put("smoke", smokestyle);
            createIndexHtml(destPath + "/212/", "212/tamList", smokestyle.getSmoid() + ".html", map1);
            list.add(smokestyle);
        }
        map.put("smokestyle", list);

    }

    //更多潭烟风貌
    private void getMoreTam() {
        createIndexHtml(destPath + "/212/", "212/index", "index.html", null);
    }


    //不同廉政文化
    private void getDifferentCultrue() {
        //资料集锦
        Map map0 = new HashMap();
        map0.put("type", 0);
        createIndexHtml(destPath + "/182/0/", "182/indexIn", "index.html", map0);
        //流程风险
        Map map1 = new HashMap();
        map1.put("type", 1);
        createIndexHtml(destPath + "/182/1/", "182/indexIn", "index.html", map1);
    }

    //廉政文化、更多
    private void getMoreCultrue() {
        createIndexHtml(destPath + "/182/", "182/index", "index.html", null);
    }


    //纪检报表更多
    private void getMoreReport() {
        createIndexHtml(destPath + "/189/", "189/index", "index.html", null);
    }

    //生成不同的风险防控模块
    private void getDifferentRisk() {
        //岗位风险
        Map map0 = new HashMap();
        map0.put("type", 0);
        createIndexHtml(destPath + "/195/0/", "195/indexIn", "index.html", map0);
        //流程风险
        Map map1 = new HashMap();
        map1.put("type", 1);
        createIndexHtml(destPath + "/195/1/", "195/indexIn", "index.html", map1);
    }

    //风险防控，更多
    private void getMoreRisk() {
        createIndexHtml(destPath + "/195/", "195/index", "index.html", null);
    }


    //通知公告
    private void loadSystemMsg(Map map) {
        List<SystemMessage> list = new ArrayList();
        IPage<SystemMessage> systemMessage = systemMessageMapper.findSystemMessage(new Page(1, 7), "", "");
        for (SystemMessage record : systemMessage.getRecords()) {
            list.add(record);
        }
        if (list.size() != 0) {
            String s = CommonUtil.delHTMLTag(list.get(0).getContent());//去除标签
            map.put("msgContent", s);
            for (SystemMessage message : list) {
                Map map1 = new HashMap();
                map1.put("message", message);
                createIndexHtml(destPath + "/211/", "211/msgList", message.getAid() + ".html", map1);
            }
        } else {
            SystemMessage message = new SystemMessage();
            message.setContent("");
            //map.put("msgContent","");
            list.add(message);
        }
        map.put("sysMessage", list);
    }

    private void cultrueList(List<CultureVo> list) {
        //Collections的sort方法默认是升序排列，如果需要降序排列时就需要重写compare方法
        Collections.sort(list, new Comparator<CultureVo>() {
            @Override
            public int compare(CultureVo o1, CultureVo o2) {
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

    // TODO 廉政文化
    private void loadCulture(Map map) {
        //资料集锦
        IPage<Datacollection> dataConllections = datacollectionMapper.findDataConllection1(new Page(1,6), "");
        //清风文苑
        IPage<Wind> winds = windMapper.findWind1(new Page(1,6), "");

        List<CultureVo> list = new ArrayList<>();
        int i0 = 0;
        for (Datacollection record : dataConllections.getRecords()) {
            CultureVo cultureVo = new CultureVo();
            cultureVo.setId(record.getDid());
            cultureVo.setContent(record.getDFileName());
            cultureVo.setTitle(record.getDTitle());
            cultureVo.setCname(record.getCname());
            cultureVo.setType(0); //资料集锦
            cultureVo.setCreateTime(record.getDCreateTime());
            if(record.getDPdf()!=null&&!record.getDPdf().equals("")){
                cultureVo.setUrl(record.getDPdf());
            }else{
                cultureVo.setUrl("null");
            }
            if(record.getDVideo()!=null&&!record.getDVideo().equals("")){
                cultureVo.setDVideo(record.getDVideo());
            }else{
                cultureVo.setDVideo("null");
            }
            list.add(cultureVo);
            Map map1 = new HashMap();
            map1.put("cultures", cultureVo);
            if (i0 == 0) {
                createIndexHtml(destPath + "/182/" + cultureVo.getType() + "/", "182/culList", cultureVo.getId() + ".html", map1);
            }
            i0++;
        }
        int i1 = 0;
        for (Wind record : winds.getRecords()) {
            CultureVo cultureVo = new CultureVo();
            cultureVo.setId(record.getWid());
            cultureVo.setContent(record.getWContent());
            cultureVo.setTitle(record.getWTitle());
            cultureVo.setCname(record.getCname());
            cultureVo.setType(1); //清风文苑
            cultureVo.setCreateTime(record.getWCreateTime());
            list.add(cultureVo);
            Map map1 = new HashMap();
            map1.put("cultures", cultureVo);
            if (i1 == 0) {
                createIndexHtml(destPath + "/182/" + cultureVo.getType() + "/", "182/culList", cultureVo.getId() + ".html", map1);
            }
            i1++;
        }
        //排序
        cultrueList(list);
        if (list.size() != 0) {
            String s = CommonUtil.delHTMLTag(list.get(0).getContent());//去除标签
            map.put("cuContent", s);
            if (list.size() > 0 && list.size() < 6) {
                list = list.subList(0, list.size());
            } else if (list.size() >= 6) {
                list = list.subList(0, 6);
            }
            for (CultureVo cultureVo : list) {
                Map map1 = new HashMap();
                map1.put("cultures", cultureVo);
                if (cultureVo.getType() == 0) {
                    //清风文苑
                    createIndexHtml(destPath + "/182/" + cultureVo.getType() + "/", "182/culList", cultureVo.getId() + ".html", map1);
                } else {
                    //资料集锦
                    createIndexHtml(destPath + "/182/" + cultureVo.getType() + "/", "182/culList", cultureVo.getId() + ".html", map1);
                }
            }
        } else {
            CultureVo cultureVo = new CultureVo();
            cultureVo.setContent("");
            //map.put("cuContent","");
            list.add(cultureVo);
        }
        map.put("cultures", list);
    }

    //纪检报表
    private void loadReport(Map map) {
        IPage<DcpReport> dcpReportIndex = dcpReportMapper.findDcpReportIndex(new Page(1,6), "");
        List<DcpReport> list = new ArrayList<>();
        for (DcpReport record : dcpReportIndex.getRecords()) {
            int i = record.getReport().lastIndexOf(".");
            record.setFile(record.getReport().substring(0, i));
            list.add(record);
        }
        if (list.size() != 0) {
            if (list.size() > 0 && list.size() < 6) {
                list = list.subList(0, list.size());
            } else if (list.size() >= 6) {
                list = list.subList(0, 6);
            }
            for (DcpReport report : list) {
                Map map1 = new HashMap();
                map1.put("record", report);
                createIndexHtml(destPath + "/189/", "189/repList", report.getId() + ".html", map1);
            }
        } else {
            DcpReport report = new DcpReport();
            report.setReport("");
            list.add(report);
        }

        map.put("reports", list);
    }

    //风险防控
    private void loadRisk(Map map) {
        //流程风险
        IPage<Processrick> processrickAll = processrickMapper.findProcessrickIndex(new Page(1,6), "");
        IPage<Postriskcombing> postriskCombingAll = postriskcombingMapper.findPostriskCombingIndex(new Page(1,6), "");
        List<RiskVo> list = new ArrayList<>();
        int i0 = 0;
        for (Postriskcombing postriskcombing : postriskCombingAll.getRecords()) {
            RiskVo riskVo = new RiskVo();
            riskVo.setCreateTime(postriskcombing.getPCreateTime());
            riskVo.setDescription(postriskcombing.getPRiskPointDescription());
            riskVo.setProject(postriskcombing.getPProject());
            riskVo.setId(postriskcombing.getPid());
            riskVo.setCname(postriskcombing.getPCreateName());
            //状态
            riskVo.setType(0); //岗位风险
            list.add(riskVo);
            Map map1 = new HashMap();
            map1.put("riskVo", riskVo);
            if (i0 == 0) {
                createIndexHtml(destPath + "/195/0/", "195/riList", riskVo.getId() + ".html", map1);
            }
            i0++;

        }
        int i1 = 0;
        for (Processrick processrick : processrickAll.getRecords()) {
            RiskVo riskVo = new RiskVo();
            String s = processrick.getProInfomation();
            riskVo.setCreateTime(processrick.getProCreateTime());
            riskVo.setDescription(s);
            riskVo.setProject(processrick.getProName());
            riskVo.setId(processrick.getProid());
            riskVo.setCname(processrick.getProCreateName());
            //状态
            Map map1 = new HashMap();
            map1.put("riskVo", riskVo);
            riskVo.setType(1); //流程风险
            list.add(riskVo);
            if (i1 == 0) {
                createIndexHtml(destPath + "/195/1/", "195/riList", riskVo.getId() + ".html", map1);
            }
            i1++;
        }
        listSortriskVos(list);
        if (list.size() != 0) {
            String s = CommonUtil.delHTMLTag(list.get(0).getDescription());//去除标签
            map.put("riContent", s);
            if (list.size() > 0 && list.size() < 6) {
                list = list.subList(0, list.size());
            } else {
                list = list.subList(0, 6);
            }
            for (RiskVo riskVo : list) {
                Map map1 = new HashMap();
                map1.put("riskVo", riskVo);
                if (riskVo.getType() == 0) {
                    createIndexHtml(destPath + "/195/0/", "195/riList", riskVo.getId() + ".html", map1);
                } else {
                    createIndexHtml(destPath + "/195/1/", "195/riList", riskVo.getId() + ".html", map1);
                }
            }
        } else {
            //map.put("riContent", "");
            RiskVo riskVo = new RiskVo();
            riskVo.setDescription("");
            list.add(riskVo);
        }

        map.put("riskVos", list);
    }


    private void listSortriskVos(List<RiskVo> list) {
        //Collections的sort方法默认是升序排列，如果需要降序排列时就需要重写compare方法
        Collections.sort(list, new Comparator<RiskVo>() {
            @Override
            public int compare(RiskVo o1, RiskVo o2) {
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
        if (file.exists()) {
            //文件夹存在就删除
             file.delete();
        } else {
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


}
