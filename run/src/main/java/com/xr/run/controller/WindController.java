package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.Wind;
import com.xr.run.service.SysStaffService;
import com.xr.run.service.WindService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wind")
public class WindController {
    @Autowired
    private WindService windService;
    @Autowired
    private SysStaffService sysStaffService;
    @GetMapping("/get/{pageNum}/{pageRow}")
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
    public JSONObject updateWind(Wind wind)  {
        windService.updateWindByWid(wind);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteWindByWid(@RequestBody int[] wid)  {
        if (wid.length==1){
            windService.deleteWindByWid(wid[0]);
        }else{
            for (int i = 0; i < wid.length; i++) {
                windService.deleteWindByWid(wid[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertWind(Wind wind)  {
        windService.insertWind(wind);
        return CommonUtil.successJson("新增成功!");
    }
}
