package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.Riskpointwarning;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.RiskpointwarningService;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/riskpointwarning")
public class RiskpointwarningController {
    @Autowired
    private RiskpointwarningService riskpointwarningService;
    @Autowired
    private SysStaffService sysStaffService;
    @GetMapping("/get/{pageNum}/{pageRow}")
    public JSONObject findRiskpointwarning(@PathVariable Integer pageNum, String wTitle,String wContent, @PathVariable Integer pageRow){
        if(wTitle==null||wContent==null){
            wTitle="";
            wContent="";
        }
        System.out.println("findRiskpointwarning");
        pageNum=pageNum<1||null==pageNum?1:pageNum;
        pageRow=pageRow<5||null==pageRow?5:pageRow;
        Page<Riskpointwarning> page=new Page(pageNum,pageRow);
        IPage<Riskpointwarning> riskpointwarning = riskpointwarningService.findRiskpointwarning(page,wTitle,wContent);
        Map<String,Object> map=new HashMap<>();
        map.put("riskpointwarning",riskpointwarning);
        return CommonUtil.successJson(map);
    }
    @RequestMapping("update")
    public JSONObject updateRiskpointwarning(Riskpointwarning riskpointwarning)  {
        riskpointwarningService.updateRiskpointwarningByWid(riskpointwarning);
        return CommonUtil.successJson("修改成功!");
    }
    @RequestMapping("delete")
    public JSONObject deleteRiskpointwarningByWid(@RequestBody int[] wid)  {
        if (wid.length==1){
            riskpointwarningService.deleteRiskpointwarningByWid(wid[0]);
        }else{
            for (int i = 0; i < wid.length; i++) {
                riskpointwarningService.deleteRiskpointwarningByWid(wid[i]);
            }
        }
        return CommonUtil.successJson("删除成功!");
    }
    @RequestMapping("insert")
    public JSONObject insertRiskpointwarning(Riskpointwarning riskpointwarning)  {
        riskpointwarningService.insertRiskpointwarning(riskpointwarning);
        return CommonUtil.successJson("新增成功!");
    }
}
