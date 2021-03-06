package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.WorkPlan;
import com.xr.run.service.duty.WorkPlanService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/duty/plan")
public class WorkPlanAction {
    @Autowired
    private WorkPlanService workPlanService;
@RequestMapping("list")
 //@RequiresPermissions("duty:list")
    public ResponseResult list(){
    System.out.println("进duty:list");
        List<WorkPlan> list = workPlanService.list();
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    @RequestMapping("findbytitle")
    public ResponseResult findbytitle(String title){
        List<WorkPlan> list = workPlanService.findbytitle(title);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    @RequestMapping("addWorkPlan")
    public ResponseResult addWorkPlan(WorkPlan workPlan) throws ParseException {
        System.out.println("workPlan"+workPlan);
        Integer integer = workPlanService.addWorkPlan(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        result.getInfo().put("id",integer);
        return result;
    }
    @RequestMapping("updatecontent")
    public ResponseResult updateWorkPlan(WorkPlan workPlan) {
        workPlanService.updateWorkPlan(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subaudit")
    public ResponseResult subaudit(Integer id) {
        workPlanService.subaudit(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","提交成功");
        return result;
    }
    /*通过审核*/
    @RequestMapping("passaudit")
    public ResponseResult passaudit(WorkPlan workPlan) {
        workPlanService.passaudit(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","审核通过");
        return result;
    }
    @RequestMapping("delWorkplanByid")
    public ResponseResult del(String test) {
        System.out.println("test"+test);
       List<WorkPlan> workPlans = JSON.parseArray(test, WorkPlan.class);
        for(WorkPlan workPlan : workPlans){ // Exception
            System.out.println("workPlan"+workPlan.getId());
             workPlanService.del(workPlan.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
}
