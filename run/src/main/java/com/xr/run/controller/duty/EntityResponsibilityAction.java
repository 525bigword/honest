package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.EntityResponsibility;
import com.xr.run.service.duty.EntityResponsibilityService;
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
@RequestMapping("/duty/main")
public class EntityResponsibilityAction {
    @Autowired
    private EntityResponsibilityService entityResponsibilityService;
    /*初始化工作部署页面*/
    @RequestMapping("list")
    @RequiresPermissions("entityresponsibility:list")
    public ResponseResult list(Integer mid){
        List<EntityResponsibility> list =   entityResponsibilityService.list(mid);
        System.out.println("list"+list.size());
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*根据标题查询*/
    @RequestMapping("findbytitle")
    @RequiresPermissions("entityresponsibility:list")
    public ResponseResult listBytitle(String title,Integer mid){
        List<EntityResponsibility> list=entityResponsibilityService.findbytitle(title,mid);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*新增工作部署*/
    @RequestMapping("addEntityResponsibility")
    @RequiresPermissions("entityresponsibility:add")
    public ResponseResult addEntityResponsibility(EntityResponsibility entityResponsibility){
        System.out.println(entityResponsibility+"entityResponsibility");
        Integer integer = entityResponsibilityService.addEntityResponsibility(entityResponsibility);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        result.getInfo().put("id",integer);
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateEntityResponsibility")
    @RequiresPermissions("entityresponsibility:update")
    public ResponseResult updateEntityResponsibility(EntityResponsibility entityResponsibility){
        entityResponsibilityService.updateEntityResponsibility(entityResponsibility);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subaudit")
    public ResponseResult subaudit(Integer id){
        entityResponsibilityService.subaudit(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","已提交审核");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passaudit")
    public ResponseResult passaudit(EntityResponsibility entityResponsibility){
        entityResponsibilityService.passaudit(entityResponsibility);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","处理成功");
        return result;
    }
    /*删除工作部署*/
    @RequestMapping("del")
    @RequiresPermissions("entityresponsibility:delete")
    public ResponseResult del(String test){

        System.out.println("test"+test);
        List<EntityResponsibility> entityResponsibilities = JSON.parseArray(test, EntityResponsibility.class);
        for(EntityResponsibility entityResponsibility : entityResponsibilities){ // Exception
            System.out.println("entityResponsibility"+entityResponsibility.getId());
            entityResponsibilityService.del(entityResponsibility.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
}
