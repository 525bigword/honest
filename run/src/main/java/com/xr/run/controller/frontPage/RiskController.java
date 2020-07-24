/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RiskController
 * Author:   TSYH
 * Date:     2020-07-14 20:19
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.entity.Processrick;
import com.xr.run.entity.vo.RiskVo;
import com.xr.run.service.PostriskcombingService;
import com.xr.run.service.ProcessrickService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author TSYH
 * @create 2020-07-14
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/qt/risk")
public class RiskController {

    //岗位风险
    @Autowired
    private PostriskcombingService postriskcombingService;

    //流程风险
    @Autowired
    private ProcessrickService processrickService;


    public List<RiskVo> getList(String prProject, Integer pageNo, Integer pageSize){
        //岗位风险，type=0
        IPage<Postriskcombing> postriskCombingAll = postriskcombingService.findPostriskCombingIndex1(new Page(pageNo,pageSize), prProject);
        //流程风险，type=1
        IPage<Processrick> processrickAll = processrickService.findProcessrickIndex1(new Page(pageNo,pageSize), prProject);

        List<RiskVo> list = new ArrayList<>();
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
        }
        for (Processrick processrick : processrickAll.getRecords()) {
            RiskVo riskVo = new RiskVo();
            String s = processrick.getProInfomation();
            riskVo.setCreateTime(processrick.getProCreateTime());
            riskVo.setDescription(s);
            riskVo.setProject(processrick.getProName());
            riskVo.setId(processrick.getProid());
            riskVo.setCname(processrick.getProCreateName());

            //状态
            riskVo.setType(1); //流程风险
            list.add(riskVo);
        }
        //排序
        listSortriskVos(list);
        return list;
    }

    //不同的风险
    @RequestMapping("/getRiskByType")
    public String getRiskByType(String prProject,Integer pageNo,Integer pageSize,Integer type){
        List<RiskVo> list1 = getList(prProject,pageNo,pageSize);
        if(type==0){
            //岗位
            IPage<Postriskcombing> postriskCombingIndex = postriskcombingService.findPostriskCombingIndex1(new Page(pageNo, pageSize), prProject);
            List<RiskVo> list = new ArrayList<>();
            for (Postriskcombing postriskcombing : postriskCombingIndex.getRecords()) {
                RiskVo riskVo = new RiskVo();
                riskVo.setFirstPage(list1.get(0).getId());
                riskVo.setFirstType(list1.get(0).getType());
                riskVo.setCreateTime(postriskcombing.getPCreateTime());
                riskVo.setDescription(postriskcombing.getPRiskPointDescription());
                riskVo.setProject(postriskcombing.getPProject());
                riskVo.setId(postriskcombing.getPid());
                riskVo.setCname(postriskcombing.getPCreateName());
                riskVo.setPname(postriskcombing.getPname());
                riskVo.setMname(postriskcombing.getMname());
                riskVo.setGrade(postriskcombing.getPGrade()); //风险级别
                riskVo.setMeasures(postriskcombing.getPMeasures()); //措施
                riskVo.setPic(""); //图片

                //状态
                riskVo.setType(0); //岗位风险
                list.add(riskVo);
            }
            return  "{\"total\":" + postriskCombingIndex.getTotal() + ",\"pages\":" + postriskCombingIndex.getPages() + ",\"rows\":" + JSON.toJSONString(list) + "}";
        }else{
            //流程
            List<RiskVo> list = new ArrayList<>();
            IPage<Processrick> processrickAll = processrickService.findProcessrickIndex1(new Page(pageNo, pageSize), prProject);
            for (Processrick processrick : processrickAll.getRecords()) {
                RiskVo riskVo = new RiskVo();
                riskVo.setFirstPage(list1.get(0).getId());
                riskVo.setFirstType(list1.get(0).getType());
                String s = processrick.getProInfomation();
                riskVo.setCreateTime(processrick.getProCreateTime());
                riskVo.setDescription(s);
                riskVo.setProject(processrick.getProName());
                riskVo.setId(processrick.getProid());
                riskVo.setCname(processrick.getProCreateName());
                riskVo.setGrade(processrick.getProGrade()); //风险级别
                riskVo.setMeasures(processrick.getProMeasures()); //措施
                riskVo.setPname(processrick.getPname());
                riskVo.setMname(processrick.getMname());
                riskVo.setPic(processrick.getProAccessory()); //图片

                //状态
                riskVo.setType(1); //流程风险
                list.add(riskVo);
            }
            return  "{\"total\":" + processrickAll.getTotal() + ",\"pages\":" + processrickAll.getPages() + ",\"rows\":" + JSON.toJSONString(list) + "}";
        }
    }

    //根据id,type一条记录
    @RequestMapping("/getRiskById")
    public String getRiskByIdType(String type,String id){
        if (id != null && !id.equals("null") && !id.equals("") && type != null && !type.equals("null") && !type.equals("")) {
           if(Integer.parseInt(type)==0){
               //0是岗位
               Postriskcombing post = postriskcombingService.findByPid(Integer.parseInt(id));
               RiskVo riskVo = new RiskVo();
               riskVo.setCreateTime(post.getPCreateTime());
               riskVo.setDescription(post.getPRiskPointDescription());
               riskVo.setProject(post.getPProject());
               riskVo.setId(post.getPid());
               riskVo.setCname(post.getPCreateName());
               riskVo.setGrade(post.getPGrade()); //风险级别
               riskVo.setMeasures(post.getPMeasures()); //措施
               riskVo.setPname(post.getPname());
               riskVo.setMname(post.getMname());
               riskVo.setPic(""); //图片

               //状态
               riskVo.setType(0); //岗位风险

               return JSON.toJSONString(riskVo);

           }else{
               //1是流程
               Processrick processrick = processrickService.findProcessrickById(Integer.parseInt(id));
               RiskVo riskVo = new RiskVo();
               riskVo.setCreateTime(processrick.getProCreateTime());
               String s = processrick.getProInfomation();
               riskVo.setCreateTime(processrick.getProCreateTime());
               riskVo.setDescription(s);
               riskVo.setProject(processrick.getProName());
               riskVo.setId(processrick.getProid());
               riskVo.setCname(processrick.getProCreateName());
               riskVo.setGrade(processrick.getProGrade()); //风险级别
               riskVo.setMeasures(processrick.getProMeasures()); //措施
               riskVo.setPname(processrick.getPname());
               riskVo.setMname(processrick.getMname());
               riskVo.setPic(processrick.getProAccessory()); //图片

               //状态
               riskVo.setType(1); //流程风险
               return JSON.toJSONString(riskVo);
           }
        }
        return "null";
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

                }
                return 0;
            }
        });
    }






}