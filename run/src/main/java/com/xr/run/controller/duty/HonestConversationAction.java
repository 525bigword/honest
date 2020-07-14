package com.xr.run.controller.duty;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.duty.HonestConversation;
import com.xr.run.service.duty.HonestConversationService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        System.out.println(honestConversation+"honestConversation");
        honestConversationService.addHonestConversation(honestConversation);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
    /*更新工作部署*/
    @RequestMapping("updateHonestConversation")
    public ResponseResult updateHonestConversation (HonestConversation honestConversation)throws ParseException {
        honestConversationService.updateHonestConversation(honestConversation);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*提交审核*/
    @RequestMapping("subauditHonestConversation")
    public ResponseResult subauditHonestConversation(Integer id){
        System.out.println("id"+id);
        System.out.println("提交");
        honestConversationService.subauditHonestConversation(id);
        ResponseResult result=new ResponseResult();
      //  result.getData().put("message","提交成功");
        return result;
    }
    /*审核通过*/
    @RequestMapping("passauditHonestConversation")
    public ResponseResult passauditHonestConversation(Integer id,Integer status,String auditresult){
        System.out.println("审核 id="+id+",status="+status);
        honestConversationService.passauditHonestConversation(id,status,auditresult);
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
        result.getInfo().put("message","删除成功");
        return result;
    }
    @RequestMapping("initpersons")
    public ResponseResult findallduty(Integer id){
        List<SysStaff> list=honestConversationService.findallduty(id);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;

    }

    @RequestMapping("getFileGroup")
    public ResponseResult getFileGroup(Integer parent){
        ResponseResult result=new ResponseResult();
        List<SysMechanism> findallunit = honestConversationService.findallunit(parent);
        result.getInfo().put("list",findallunit);
        return result;
    }
   // @Value("${files.uploadFolder}")
    private String realBasePath="D://IDE/";
    @Value("${file.staticAccessPaths}")
    private String accessPath;
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @RequestMapping("uploading")
    public ResponseResult uploadFile(@RequestParam("filename") MultipartFile file, HttpServletRequest req) throws IOException {
        ResponseResult result=new ResponseResult();
        String format = sdf.format(new Date());
       // String realPath = req.getServletContext().getRealPath("/honest/upload") + format;
        // 域名访问的相对路径（通过浏览器访问的链接-虚拟路径）
        String saveToPath = accessPath + format;
        // 真实路径，实际储存的路径
        String realPath = realBasePath + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
      //  String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/honest/upload" + format + newName;
        String url = req.getScheme() + "://" + req.getServerName() + ":8080/upload/"+format  + newName;
        System.out.println(url);
        result.getInfo().put("url",url);
        result.getInfo().put("message","上传成功");
        return result;
    }
}
