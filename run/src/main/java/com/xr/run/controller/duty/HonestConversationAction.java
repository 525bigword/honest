package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.duty.HonestConversation;
import com.xr.run.service.duty.HonestConversationService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/duty/talk")
public class HonestConversationAction {
    @Autowired
    private HonestConversationService honestConversationService;
    /*初始化工作部署页面*/
    @RequestMapping("list")
    public ResponseResult list(){
        List<HonestConversation> list =   honestConversationService.list();
        System.out.println("list"+list.size());
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*根据谈话类型查询*/
    @RequestMapping("findbytitle")
    public ResponseResult listBytitle(String title){
        System.out.println("title"+title);
        List<HonestConversation> list=honestConversationService.listBytitle(title);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        result.getInfo().put("total",list.size());
        return result;
    }
    /*新增工作部署*/
    @RequestMapping("addHonestConversation")
    public ResponseResult addHonestConversation(HonestConversation honestConversation){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = SDF.parse(honestConversation.getCreatetime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 8);// 24小时制
        date = cal.getTime();
        cal = null;
        honestConversation.setCreatetime(format.format(date));
        System.out.println(honestConversation+"honestConversation");
        honestConversationService.addHonestConversation(honestConversation);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateHonestConversation")
    public ResponseResult updateHonestConversation(HonestConversation honestConversation){
        honestConversationService.updateHonestConversation(honestConversation);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subauditHonestConversation")
    public ResponseResult subauditHonestConversation(Integer id){
        honestConversationService.subauditHonestConversation(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","提交成功");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passauditHonestConversation")
    public ResponseResult passauditHonestConversation(Integer id){
        honestConversationService.passauditHonestConversation(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","审核通过");
        return result;
    }
    /*删除工作部署*/
    @RequestMapping("delhonestConversation")
    public ResponseResult delDeployment(String test){

        System.out.println("test"+test);
        List<HonestConversation> honestConversations = JSON.parseArray(test, HonestConversation.class);
        for(HonestConversation honestConversation : honestConversations){ // Exception
            System.out.println("honestConversation"+honestConversation.getId());
            honestConversationService.delHonestConversationt(honestConversation.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("msg","删除成功");
        return result;
    }
}
