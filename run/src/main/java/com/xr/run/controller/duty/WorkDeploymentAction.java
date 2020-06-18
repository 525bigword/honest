package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.WorkDeployment;
import com.xr.run.service.duty.WorkDeploymentService;
import com.xr.run.util.ResponseResult;
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
    public ResponseResult list(){
        List<WorkDeployment> list =   workDeploymentService.list();
        System.out.println("list"+list.size());
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*根据标题查询*/
    @RequestMapping("findbytitle")
    public ResponseResult listBytitle(String title){
        List<WorkDeployment> list=workDeploymentService.listBytitle(title);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*新增工作部署*/
    @RequestMapping("addDeployment")
    public ResponseResult addDeployment(WorkDeployment workDeployment){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = SDF.parse(workDeployment.getCreatetime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        cal = null;
        workDeployment.setCreatetime(format.format(date));
        System.out.println(workDeployment+"workDeployment");
        workDeploymentService.addDeployment(workDeployment);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateDeployment")
    public ResponseResult updateDeployment(WorkDeployment workDeployment){
        workDeploymentService.updateDeployment(workDeployment);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subauditDeployment")
    public ResponseResult subauditDeployment(Integer id){
        workDeploymentService.subauditDeployment(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","提交成功");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passauditDeployment")
    public ResponseResult passauditDeployment(Integer id){
        workDeploymentService.passauditDeployment(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","审核通过");
        return result;
    }
    /*删除工作部署*/
    @RequestMapping("delDeployment")
    public ResponseResult delDeployment(String test){

        System.out.println("test"+test);
        List<WorkDeployment> workDeployments = JSON.parseArray(test, WorkDeployment.class);
        for(WorkDeployment workDeployment : workDeployments){ // Exception
            System.out.println("WorkDeployment"+workDeployment.getId());
            workDeploymentService.delDeployment(workDeployment.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("msg","删除成功");
        return result;
    }
}
