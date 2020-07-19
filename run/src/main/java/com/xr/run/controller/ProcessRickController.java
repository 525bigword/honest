package com.xr.run.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.xr.run.entity.Processrick;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.ProcessrickService;
import com.xr.run.util.ResponseResult;
import com.xr.run.util.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 12046
 * @create 2020/6/29
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/processRick")
public class ProcessRickController {

    @Autowired
    private ProcessrickService processrickService;


    @RequestMapping("getList")
    @RequiresPermissions("processRick:list")
    public ResponseResult list() {
        List<Processrick> list = processrickService.getList();
        ResponseResult result = new ResponseResult();
        result.getInfo().put("list", list);
        result.getInfo().put("total", list.size());
        return result;
    }

    @PostMapping("add")
    @RequiresPermissions("processRick:add")
    public ResponseResult add(@RequestBody Processrick processrick) {
        log.info("=================================================="+processrick.toString());
        log.info("=================================================="+processrick.getProName());
        //processrick.setProYear(processrick.getProYear().substring(0,4));
        ResponseResult result = new ResponseResult();
        if (processrick == null) {
            result.getInfo().put("message", "需要添加的数据为NULL");
            return result;
        }

        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff userInfo = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);
        processrick.setProCreateId(userInfo.getSid());
        processrick.setProCreateName(userInfo.getName());
        //System.out.println(processrick);
        processrickService.addProcessrick(processrick);
        result.getInfo().put("message", "新增成功");
        return result;
    }

    @RequestMapping("delete")
    @RequiresPermissions("processRick:delete")
    public ResponseResult deletePostRiskCombing(String test) {
        List<Processrick> processrickList = JSON.parseArray(test, Processrick.class);
        for (Processrick processrick : processrickList) {
            processrickService.deleteByProid(processrick.getProid());
        }
        ResponseResult result = new ResponseResult();
        result.getInfo().put("message", "删除成功");
        return result;
    }

    @PostMapping(value = "update")
    @RequiresPermissions("processRick:update")
    public ResponseResult updatePostRiskCombing(Processrick processrick) {
       // System.out.println(processrick);
        log.info("==========================================="+processrick.toString());
        processrick.setProYear(processrick.getProYear().substring(0,4));
        ResponseResult result = new ResponseResult();
        if (processrick != null) {
            processrickService.updateByProid(processrick);
            result.getInfo().put("message", "修改成功");
        } else {
            result.getInfo().put("message", "修改失败");
        }
        return result;
    }

    @RequestMapping("findBy")
    @RequiresPermissions("processRick:list")
    public ResponseResult findBy(String proName, String proYear,String proGrade) {
        if(proYear!=null){
            proYear=proYear.substring(0,4);
        }
        ResponseResult responseResult = new ResponseResult();
        List<Processrick> list = processrickService.getListBy(proName, proYear,proGrade);
        responseResult.getInfo().put("list", list);
        responseResult.getInfo().put("total", list.size());
        return responseResult;
    }
}
