package com.xr.run.controller.daily;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.daily.Supervisionfiling;
import com.xr.run.service.daily.SupervisionfilingService;
import com.xr.run.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/daily/supervisionfiling")
public class SupervisionfilingAction {
    @Autowired
    private SupervisionfilingService supervisionfilingService;
    @RequestMapping("list")
    @RequiresPermissions("supervise:list")
    public ResponseResult list(Integer sStatus){
        List<Supervisionfiling> list = supervisionfilingService.list(sStatus);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;

    }
    @RequestMapping("signleader")
    public ResponseResult signleader(Integer sid){
        supervisionfilingService.signleader(sid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","验收完毕");
        return result;
    }
    @RequestMapping("findbytitle")
    @RequiresPermissions("supervise:list")
    public ResponseResult findbytitle(String sPaperItems,Integer sStatus){
        List<Supervisionfiling> list = supervisionfilingService.findbytitle(sPaperItems,sStatus);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;
    }
    @RequestMapping("findbysFilingId")
    public ResponseResult findbysFilingId(String sFilingId, Integer sStatus){
        List<Supervisionfiling> list = supervisionfilingService.findbysFilingId(sFilingId,sStatus);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;
    }
    @RequestMapping("addsupervisionfiling")
    @RequiresPermissions("supervise:add")
    public ResponseResult addsupervisionfiling(Supervisionfiling supervisionfiling){
        supervisionfilingService.addsupervisionfiling(supervisionfiling);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    /*删除*/
    @RequestMapping("delesupervision")
    @RequiresPermissions("supervise:delete")
    public ResponseResult delesupervision(String test){
        System.out.println("test"+test);
        List<Supervisionfiling> supervisionfilings = JSON.parseArray(test, Supervisionfiling.class);
        for(Supervisionfiling supervisionfiling : supervisionfilings){ // Exception
            System.out.println("supervisionfiling"+supervisionfiling.getSid());
            supervisionfilingService.delesupervision(supervisionfiling.getSid());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("tjshme")
    public ResponseResult tjshme(Integer sid){
        supervisionfilingService.tjshme(sid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","提交审核成功");
        return result;
    }
    /*更新*/
    @RequestMapping("updatesupervision")
    @RequiresPermissions("supervise:update")
    public ResponseResult updatesupervision(Supervisionfiling supervisionfiling){
        supervisionfilingService.updatesupervision(supervisionfiling);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    @RequestMapping("tgsh")
    public ResponseResult tgsh(Supervisionfiling supervisionfiling){
        supervisionfilingService.tgsh(supervisionfiling);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","审核完成");
        return result;
    }
}
