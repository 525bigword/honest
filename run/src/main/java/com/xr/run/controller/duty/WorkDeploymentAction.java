package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.WorkDeployment;
import com.xr.run.service.duty.WorkDeploymentService;
import com.xr.run.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("duty/deploy")
public class WorkDeploymentAction {
    @Autowired
    private WorkDeploymentService workDeploymentService;
    /*初始化工作部署页面*/
    @RequestMapping("list")
    @RequiresPermissions("workdeployment:list")
    public ResponseResult list(Integer mid){
        List<WorkDeployment> list =   workDeploymentService.list(mid);
        System.out.println("list"+list.size());
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*根据标题查询*/
    @RequestMapping("findbytitle")
    @RequiresPermissions("workdeployment:list")
    public ResponseResult listBytitle(String title,Integer mid){
        List<WorkDeployment> list=workDeploymentService.listBytitle(title,mid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*新增工作部署*/
    @RequestMapping("addDeployment")
    @RequiresPermissions("workdeployment:add")
    public ResponseResult addDeployment(WorkDeployment workDeployment){
        System.out.println(workDeployment+"workDeployment");
        Integer integer = workDeploymentService.addDeployment(workDeployment);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        result.getInfo().put("id",integer);
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateDeployment")
    @RequiresPermissions("workdeployment:update")
    public ResponseResult updateDeployment(WorkDeployment workDeployment){
        workDeploymentService.updateDeployment(workDeployment);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subauditDeployment")
    @RequiresPermissions("workdeployment:add")
    public ResponseResult subauditDeployment(Integer id){
        workDeploymentService.subauditDeployment(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","已提交审核");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passauditDeployment")
    @RequiresPermissions("workdeployment:audit")
    public ResponseResult passauditDeployment(WorkDeployment workDeployment){
        workDeploymentService.passauditDeployment(workDeployment);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","处理成功");
        return result;
    }
    /*删除工作部署*/
    @RequestMapping("delDeployment")
    @RequiresPermissions("workdeployment:delete")
    public ResponseResult delDeployment(String test){

        System.out.println("test"+test);
        List<WorkDeployment> workDeployments = JSON.parseArray(test, WorkDeployment.class);
        for(WorkDeployment workDeployment : workDeployments){ // Exception
            System.out.println("WorkDeployment"+workDeployment.getId());
            workDeploymentService.delDeployment(workDeployment.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
}
