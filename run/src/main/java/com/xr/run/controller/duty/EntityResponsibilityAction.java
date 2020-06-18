package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.EntityResponsibility;
import com.xr.run.service.duty.EntityResponsibilityService;
import com.xr.run.util.ResponseResult;
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
    public ResponseResult list(){
        List<EntityResponsibility> list =   entityResponsibilityService.list();
        System.out.println("list"+list.size());
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*根据标题查询*/
    @RequestMapping("findbytitle")
    public ResponseResult listBytitle(String title){
        List<EntityResponsibility> list=entityResponsibilityService.findbytitle(title);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*新增工作部署*/
    @RequestMapping("addEntityResponsibility")
    public ResponseResult addEntityResponsibility(EntityResponsibility entityResponsibility){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = SDF.parse(entityResponsibility.getCreatetime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        cal = null;
        entityResponsibility.setCreatetime(format.format(date));
        System.out.println(entityResponsibility+"entityResponsibility");
       entityResponsibilityService.addEntityResponsibility(entityResponsibility);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateEntityResponsibility")
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
        result.getInfo().put("message","提交成功");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passaudit")
    public ResponseResult passaudit(Integer id){
        entityResponsibilityService.passaudit(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","审核通过");
        return result;
    }
    /*删除工作部署*/
    @RequestMapping("del")
    public ResponseResult del(String test){

        System.out.println("test"+test);
        List<EntityResponsibility> entityResponsibilities = JSON.parseArray(test, EntityResponsibility.class);
        for(EntityResponsibility entityResponsibility : entityResponsibilities){ // Exception
            System.out.println("entityResponsibility"+entityResponsibility.getId());
            entityResponsibilityService.del(entityResponsibility.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("msg","删除成功");
        return result;
    }
}
