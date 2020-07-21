package com.xr.run.controller.frontPage;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.*;
import com.xr.run.entity.vo.CultureVo;
import com.xr.run.entity.vo.RdWorkVo;
import com.xr.run.service.DatacollectionService;
import com.xr.run.service.WindService;
import com.xr.run.util.CommonUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/qt/qtwind")
public class FontWindController {
    @Autowired
    private DatacollectionService datacollectionService;
    @Autowired
    private WindService windService;

    @RequestMapping("/getByType")
    public String getByType(Integer type, String title, Integer pageNo, Integer pageSize) {
        if (type == 0) {
            //资料集锦
            List<CultureVo> list = new ArrayList<>();
            IPage<Datacollection> dataConllections = datacollectionService.findDataConllection1(new Page(pageNo, pageSize), title);
            for (Datacollection record : dataConllections.getRecords()) {
                CultureVo cultureVo = new CultureVo();
                cultureVo.setId(record.getDid());
                cultureVo.setContent(record.getDFileName());
                cultureVo.setTitle(record.getDTitle());
                cultureVo.setCname(record.getCname());
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
                cultureVo.setType(0); //资料集锦
                cultureVo.setCreateTime(record.getDCreateTime());
                list.add(cultureVo);
            }
            return "{\"total\":" + dataConllections.getTotal() + ",\"pages\":" + dataConllections.getPages() + ",\"rows\":" + JSON.toJSONString(list) + "}";
        } else {
            //清风文苑
            List<CultureVo> list = new ArrayList<>();
            IPage<Wind> winds = windService.findWind1(new Page(pageNo, pageSize), title);
            for (Wind record : winds.getRecords()) {
                CultureVo cultureVo = new CultureVo();
                cultureVo.setId(record.getWid());
                cultureVo.setContent(record.getWContent());
                cultureVo.setTitle(record.getWTitle());
                cultureVo.setCname(record.getCname());
                cultureVo.setType(1); //清风文苑
                cultureVo.setCreateTime(record.getWCreateTime());
                list.add(cultureVo);
            }
            return "{\"total\":" + winds.getTotal() + ",\"pages\":" + winds.getPages() + ",\"rows\":" + JSON.toJSONString(list) + "}";
        }
    }


    @RequestMapping("/getCulById")
    public String getCulById(String type, String id) {
        if (id != null && !id.equals("null") && !id.equals("") && type != null && !type.equals("null") && !type.equals("")) {
            if (Integer.parseInt(type) == 0) {
                //资料集锦
                Datacollection datacollection = datacollectionService.findDatacollectionById(Integer.parseInt(id));
                CultureVo cultureVo = new CultureVo();
                cultureVo.setId(datacollection.getDid());
                cultureVo.setContent(datacollection.getDFileName());
                cultureVo.setTitle(datacollection.getDTitle());
                cultureVo.setCname(datacollection.getCname());
                cultureVo.setType(0); //资料集锦
                if(datacollection.getDPdf()!=null&&!datacollection.getDPdf().equals("")){
                    cultureVo.setUrl(datacollection.getDPdf());
                }else{
                    cultureVo.setUrl("null");
                }
                if(datacollection.getDVideo()!=null&&!datacollection.getDVideo().equals("")){
                    cultureVo.setDVideo(datacollection.getDVideo());
                }else{
                    cultureVo.setDVideo("null");
                }
                cultureVo.setCreateTime(datacollection.getDCreateTime());
                return JSON.toJSONString(cultureVo);
            } else {
                //清风文苑
                Wind windByWid = windService.findWindByWid(Integer.parseInt(id));
                CultureVo cultureVo = new CultureVo();
                cultureVo.setId(windByWid.getWid());
                cultureVo.setContent(windByWid.getWContent());
                cultureVo.setTitle(windByWid.getWTitle());
                cultureVo.setCname(windByWid.getCname());
                cultureVo.setType(1); //清风文苑
                cultureVo.setCreateTime(windByWid.getWCreateTime());
                return JSON.toJSONString(cultureVo);
            }
        }
        return"null";
}


    private void cultrueVoSort(List<CultureVo> list) {
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
                }
                return 0;
            }
        });

    }


}