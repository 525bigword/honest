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
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/doLogin")
    public String login(SysStaff sysStaff,HttpServletResponse response,HttpSession session){
        System.out.println("username="+sysStaff.getUsername());
        SysStaff user = sysStaffService.getUser(sysStaff.getUsername(), sysStaff.getPassword());
        if(user!=null){
//            //保存在Cookie中
//            Cookie myCookie=new Cookie("username",user.getUsername());
//            myCookie.setMaxAge(600*10);//30分钟
//            response.addCookie(myCookie);
            session.setAttribute("loginUser",user);

            return "HomePage";
        }
        return null;
    }




    //获取用户信息
   /* @RequestMapping("/getUser")
    public String getUser(HttpSession session){
        SysStaff sysUser = (SysStaff) session.getAttribute("sysUser");
        String user = JSON.toJSONString(sysUser);
        return user;
    }
*/



}