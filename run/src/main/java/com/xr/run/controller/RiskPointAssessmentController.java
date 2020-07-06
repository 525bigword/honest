package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.entity.Processrick;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.service.RiskpointassessmentService;
import com.xr.run.service.SysMechanismService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 12046
 * @create 2020/6/30
 * @since 1.0.0
 */
@RestController
@RequestMapping("/riskPointAssessment")
public class RiskPointAssessmentController {

    @Autowired
    private RiskpointassessmentService riskpointassessmentService;
    @Autowired
    private SysMechanismService sysMechanismService;
    @RequestMapping("getList")
    public ResponseResult list() {
        List<Riskpointassessment> list = riskpointassessmentService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

    @RequestMapping("sync")
    public ResponseResult sync() {
        riskpointassessmentService.syncRiskpointassessment();
        List<Riskpointassessment> list = riskpointassessmentService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("message", "同步成功");
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

    @RequestMapping("getOne")
    public ResponseResult getOne(String riskImfomation) {
        Riskpointassessment riskpointassessment = riskpointassessmentService.getOneByRiskImfomation(Integer.parseInt(riskImfomation));
        ResponseResult result = new ResponseResult();
        result.getInfo().put("riskpointassessment", riskpointassessment);
        return result;
    }

    @RequestMapping("findByPDeptId")
    public ResponseResult findByPDeptId(String pDeptId) {
        List<Riskpointassessment> list = riskpointassessmentService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }
    @GetMapping("echatsInfo")
    public JSONObject getEchatsInfo(String mids,Integer type){
        System.out.println(mids);
        System.out.println(type);
        String[] midList = mids.split(",");
        List<Object> sysMechanismNameByMid = sysMechanismService.findSysMechanismNameByMid(midList, type);
        return CommonUtil.successJson(sysMechanismNameByMid);
    }
}
