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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-14
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/risk")
public class RiskController {

    //岗位风险
    @Autowired
    private PostriskcombingService postriskcombingService;

    //流程风险
    @Autowired
    private ProcessrickService processrickService;

    @RequestMapping("/getRiskAll")
    public String getRiskAll(String prProject,Integer pageNo,Integer pageSize){
        //岗位风险，type=1
        IPage<Postriskcombing> postriskCombingAll = postriskcombingService.findPostriskCombingIndex(new Page(), prProject);
        //流程风险，type=0
        IPage<Processrick> processrickAll = processrickService.findProcessrickIndex(new Page(), prProject);

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
            riskVo.setType(1); //岗位风险
            list.add(riskVo);
        }
        //排序
        listSortriskVos(list);
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
            List<RiskVo> pageList = list.subList(fromIndex, toIndex);
            //第一页、第一种类型
            for (RiskVo risk : pageList) {
                risk.setFirstPage(pageList.get(0).getId());
                risk.setFirstType(pageList.get(0).getType());
            }
            String jsonString = JSON.toJSONString(pageList);
            String jso = "{\"total\":" + count + ",\"pages\":" + pageCount + ",\"rows\":" + jsonString + "}";
            return jso;
        } else {
            //没查找到返回
            return "null";
        }
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