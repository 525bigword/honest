package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Riskpointwarning;
import com.xr.run.entity.Systemprocess;
import com.xr.run.service.SysStaffService;
import com.xr.run.service.SystemprocessService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/systemprocess")
public class SystemprocessController {
    @Autowired
    private SystemprocessService systemprocessService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findSystemprocessService(@PathVariable Integer pageNum, String sysTitle, String sysContent, @PathVariable Integer pageRow){
        if(sysTitle==null||sysContent==null){
            sysTitle="";
            sysContent="";
        }
        System.out.println("findSystemprocessService");
        System.out.println("sadsah");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Systemprocess> page=new Page(pageNum,pageRow);
        IPage<Systemprocess> systemprocess = systemprocessService.findSystemprocess(page, sysTitle, sysContent);
        Map<String,Object> map=new HashMap<>();
        map.put("systemprocess",systemprocess);
        return CommonUtil.successJson(map);
    }
    @RequestMapping("update")
    public JSONObject updateSystemprocess(Systemprocess systemprocess)  {
        systemprocessService.updateSystemprocessBySysId(systemprocess);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteSystemprocessBySysid(@RequestBody int[] sysid)  {
        if (sysid.length==1){
           systemprocessService.deleteSystemprocessBySysId(sysid[0]);
        }else{
            for (int i = 0; i < sysid.length; i++) {
                systemprocessService.deleteSystemprocessBySysId(sysid[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertSystemprocess(Systemprocess systemprocess)  {
        systemprocessService.insertSystemprocess(systemprocess);
        return CommonUtil.successJson("新增成功!");
    }
}
