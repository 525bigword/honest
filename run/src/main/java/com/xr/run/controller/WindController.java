package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.Wind;
import com.xr.run.service.HomePageSevice;
import com.xr.run.service.StaticHtmlService;
import com.xr.run.service.SysStaffService;
import com.xr.run.service.WindService;
import com.xr.run.util.CommonUtil;
import com.xr.run.util.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wind")
public class WindController {
    @Value("${html.destPath}")
    private String destPath="";
    public void a(){
        System.out.println(destPath);
    }
    @Autowired
    private HomePageSevice homePageSevice;
    @Autowired
    private WindService windService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private StaticHtmlService staticHtmlService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    @RequiresPermissions("wind:list")
    public JSONObject findWind(@PathVariable Integer pageNum, String wTitle, @PathVariable Integer pageRow){
        if(wTitle==null){
            wTitle="";
        }
        System.out.println("findWind");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;

        Page<Wind> page=new Page(pageNum,pageRow);
        IPage<Wind> wind = windService.findWind(page, wTitle);
        List<SysStaff> sysStaff = sysStaffService.findSysStaff();
        Map<String,Object> map=new HashMap<>();
        map.put("wind",wind);
            map.put("sys",sysStaff);
        return CommonUtil.successJson(map);
    }
    @RequestMapping("update")
    @RequiresPermissions("wind:update")
    public JSONObject updateWind(Wind wind,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        windService.updateWindByWid(wind);
        thymeleafWind(wind,httpServletRequest,httpServletResponse);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    @RequiresPermissions("wind:delete")
    public JSONObject deleteWindByWid(@RequestBody int[] wid)  {
        if (wid.length==1){
            Wind windByWid = windService.findWindByWid(wid[0]);
            staticHtmlService.deleteHtmlPage(destPath+"\\182\\183\\"+windByWid.getWid()+".html");
            windService.deleteWindByWid(wid[0]);
        }else{
            for (int i = 0; i < wid.length; i++) {
                Wind windByWid = windService.findWindByWid(wid[i]);
                staticHtmlService.deleteHtmlPage(destPath+"\\182\\183\\"+windByWid.getWid()+".html");
                windService.deleteWindByWid(wid[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    @RequiresPermissions("wind:add")
    public JSONObject insertWind(Wind wind,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  {
        windService.insertWind(wind);
        System.out.println(wind.getWid());
        thymeleafWind(wind,httpServletRequest,httpServletResponse);
        return CommonUtil.successJson("新增成功!");
    }

    public void thymeleafWind(Wind wind, HttpServletRequest req, HttpServletResponse resp){
        ModelAndView modelAndView=new ModelAndView();
            Wind windByWid = windService.findWindByWid(wind.getWid());
            modelAndView.addObject("content",windByWid.getWContent());
            modelAndView.addObject("title",windByWid.getWTitle());
            modelAndView.addObject("time", DateUtil.upDate(windByWid.getWCreateTime()));
            modelAndView.addObject("name", windByWid.getSysStaff().getName());
            modelAndView.setViewName("182/Info");
            staticHtmlService.genHtmlPage(destPath+"\\182\\183\\",modelAndView,req,resp,windByWid.getWid()+"");

    }
}
