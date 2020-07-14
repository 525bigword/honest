package com.xr.run.controller.educationPolitics;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.educationpolitics.EducationPolitics;
import com.xr.run.service.educationPolitics.EducationPoliticsService;
import com.xr.run.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education/politics")
public class EducationPoliticsController {
    @Autowired
    private EducationPoliticsService educationPoliticsService;
    /*查询廉政教育*/
    @RequestMapping("findAllEducation")
    @RequiresPermissions("educationpolitics:list")
    public ResponseResult findAllEducation(){
        List<EducationPolitics> list = educationPoliticsService.findAllEducation();
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;
    }

    /*多条件查询廉政教育*/
    @RequestMapping("findwhereEducation")
    @RequiresPermissions("educationpolitics:list")
    public ResponseResult findwhereEducation(EducationPolitics educationPolitics){
        List<EducationPolitics> list = educationPoliticsService.findwhereEducation(educationPolitics);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;
    }
    /*更新廉政教育*/
    @RequestMapping("updateEducation")
    @RequiresPermissions("educationpolitics:update")
    public ResponseResult updateEducation(EducationPolitics educationPolitics){
        educationPoliticsService.updateEducation(educationPolitics);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","更新成功");
        return result;
    }
    /*删除廉政教育*/
    @RequestMapping("delEcucation")
    @RequiresPermissions("educationpolitics:delete")
    public ResponseResult delEcucation(String test){
        System.out.println("test"+test);
        List<EducationPolitics> educationPoliticss = JSON.parseArray(test, EducationPolitics.class);
        for(EducationPolitics educationPolitics : educationPoliticss){ // Exception
            System.out.println("supervisionfiling"+educationPolitics.getId());
            educationPoliticsService.delEcucation(educationPolitics.getId());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }
    /*新增廉政教育信息*/
    @RequestMapping("addEcucation")
    @RequiresPermissions("educationpolitics:add")
    public ResponseResult addEcucation(EducationPolitics educationPolitics){
        educationPoliticsService.addEcucation(educationPolitics);
        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","新增成功");
        return result;
    }
}
