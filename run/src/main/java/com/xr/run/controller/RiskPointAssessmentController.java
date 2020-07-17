package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.entity.Processrick;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysPost;
import com.xr.run.service.RiskpointassessmentService;
import com.xr.run.service.SysMechanismService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.ResponseResult;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("riskpointassessment:list")
    public ResponseResult list() {
        List<Riskpointassessment> list = riskpointassessmentService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

    @RequestMapping("findBy")
    public ResponseResult findBy(Integer riskImfomation) {
        System.out.println("DEPTID:"+riskImfomation);
        Integer num1=0;
        Integer num2=0;
        Integer num3=0;
        if(riskImfomation==null){
            List<Riskpointassessment> list = riskpointassessmentService.getList();
            System.out.println("list:"+list);
            for (int i=0;i<list.size();i++){
                num1+=list.get(i).getNumberOneRisks();
                num2+=list.get(i).getNumberTwoRisks();
                num3+=list.get(i).getNumberThreeRisks();
            }
            System.out.println(num1+"  "+num2+"  "+num3);
        }else {
            Riskpointassessment riskpointassessment = riskpointassessmentService.findBy(riskImfomation);
            System.out.println("图表riskpointassessment:" + riskpointassessment);
            num1=riskpointassessment.getNumberOneRisks();
            num2=riskpointassessment.getNumberTwoRisks();
            num3=riskpointassessment.getNumberThreeRisks();
        }
        ResponseResult result = new ResponseResult();
        result.getInfo().put("num1",num1 );
        result.getInfo().put("num2", num2);
        result.getInfo().put("num3", num3);
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
        System.out.println("riskImfomation:"+riskImfomation);
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
