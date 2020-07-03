package com.xr.run.controller;

import com.xr.run.entity.Processrick;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.service.RiskpointassessmentService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
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

}
