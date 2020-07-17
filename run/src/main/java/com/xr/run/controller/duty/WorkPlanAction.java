package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.WorkPlan;
import com.xr.run.service.duty.WorkPlanService;
import com.xr.run.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("workplan:list")
    public ResponseResult list(Integer mid){
        System.out.println("进duty:list");
        List<WorkPlan> list = workPlanService.list(mid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    @RequestMapping("findbytitle")
    @RequiresPermissions("workplan:list")
    public ResponseResult findbytitle(String title,Integer mid){
        List<WorkPlan> list = workPlanService.findbytitle(title,mid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    @RequestMapping("addWorkPlan")
    @RequiresPermissions("workplan:add")
    public ResponseResult addWorkPlan(WorkPlan workPlan) throws ParseException {
        System.out.println("workPlan"+workPlan);
        Integer integer = workPlanService.addWorkPlan(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        result.getInfo().put("id",integer);
        return result;
    }
    @RequestMapping("updatecontent")
    @RequiresPermissions("workplan:update")
    public ResponseResult updateWorkPlan(WorkPlan workPlan) {
        workPlanService.updateWorkPlan(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subaudit")
    @RequiresPermissions("workplan:add")
    public ResponseResult subaudit(Integer id) {
        workPlanService.subaudit(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","已提交审核");
        return result;
    }
    /*通过审核*/
    @RequestMapping("passaudit")
    @RequiresPermissions("workplan:audit")
    public ResponseResult passaudit(WorkPlan workPlan) {
        workPlanService.passaudit(workPlan);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","处理成功");
        return result;
    }
    @RequestMapping("delWorkplanByid")
    @RequiresPermissions("workplan:delete")
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
