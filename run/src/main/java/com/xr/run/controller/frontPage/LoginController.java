/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: LoginController
 * Author:   TSYH
 * Date:     2020-07-17 1:41
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xr.run.dao.SysStaffMapper;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-17
 * @since 1.0.0
 */
@Controller
@Slf4j
@RequestMapping("/qt/login")
public class LoginController {


    @Autowired
    private SysStaffService sysStaffService;


    /*
    * /toIndex() controller里的方法
    * 查询出所有的首页需要的数据
    * 设置到请求中
    * 带到templates/index.html
    * index.html中使用th标签动态读取和显示
    * */
    @PostMapping("/doLogin")
    @ResponseBody
    public JSONObject login(SysStaff sysStaff){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",sysStaff.getUsername());
        jsonObject.put("password",sysStaff.getPassword());
        JSONObject jsonObject1 = sysStaffService.authLogin(jsonObject);
        log.info("============================================jsonObject1.getString(\"result\")==="+jsonObject1.toJSONString());
        Object info = jsonObject1.get("info");
        JSONObject infoo = JSON.parseObject(info.toString());
        log.info("infoo========================infoo======"+infoo.getString("result"));
        if(infoo.getString("result").equals("success")){
            JSONObject msg = sysStaffService.getInfo();
            return msg;
        }
        return null;
    }
    


    //验证初始密码是否与数据库一致
   @RequestMapping("/checkOrignPass")
   @ResponseBody
   public String checkPass(String oldPassword,String username){
       String md5 = new SimpleHash("md5", oldPassword, null, 2).toString();
       SysStaff sysStaff = sysStaffService.findSysByPass(username);
       String password = sysStaff.getPassword();
       if(password.equals(md5)){
           return "true";
       }else{
           return "false";
       }
   }


   @ResponseBody
   @RequestMapping("/changePass")
   public String changePass(String oldPassword,String newPassword,String repeatPassword,String username){
       String md5 = new SimpleHash("md5", oldPassword, null, 2).toString();
       SysStaff sysStaff = sysStaffService.findSysByPass(username);
       String password = sysStaff.getPassword();
       if(!password.equals(md5)){
           return "false";
       }
       if(!newPassword.equals(repeatPassword)){
           return "false";
       }
       String newPass = new SimpleHash("md5", newPassword, null, 2).toString();

       sysStaffService.updateSysStaff(newPass,username);


       return "true";
   }


   //退出登录
   @PostMapping("/logout")
   @ResponseBody
   public JSONObject logout() {
       return sysStaffService.logout();
   }

























}