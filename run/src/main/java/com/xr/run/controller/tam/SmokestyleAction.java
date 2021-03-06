package com.xr.run.controller.tam;

import com.alibaba.fastjson.JSON;
import com.xr.run.entity.tam.Smokestyle;
import com.xr.run.service.tam.SmokestyleService;
import com.xr.run.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/smokestyle/smoke")
public class SmokestyleAction {
    @Autowired
    private SmokestyleService smokestyleService;
    /*查询潭烟风貌*/
    @RequestMapping("/findAllSmokestyle")
    public ResponseResult findAllSmokestyle(){
        List<Smokestyle> list = smokestyleService.findAllSmokestyle();
        ResponseResult result=new ResponseResult();
        result.getInfo().put("list",list);
        return result;
    }

    /*新增*/
    @RequestMapping("/addSmokestyle")
    public ResponseResult addSmokestyle(Smokestyle smokestyle){
        ResponseResult result=new ResponseResult();
        smokestyleService.addSmokestyle(smokestyle);
        result.getInfo().put("message","新增成功");
        return result;
    }

    /*根据标题查*/
    @RequestMapping("/findSmokestyle")
    public ResponseResult findSmokestyle(String title){
        ResponseResult result=new ResponseResult();
        List<Smokestyle> list = smokestyleService.findSmokestyle(title);

        result.getInfo().put("list",list);
        return result;
    }

    /*删除*/
    @RequestMapping("/delSmokestyle")
    public ResponseResult delSmokestyle(String test){
        List<Smokestyle> smokestyles = JSON.parseArray(test, Smokestyle.class);
        for(Smokestyle smokestyle : smokestyles){ // Exception
            System.out.println("smokestyle"+smokestyle.getSmoid());
            smokestyleService.delSmokestyle(smokestyle.getSmoid());
        }

        ResponseResult result=new ResponseResult();
        result.getInfo().put("message","删除成功");
        return result;
    }

    /*更新*/
    @RequestMapping("/gxSmokestyle")
    public ResponseResult gxSmokestyle(Smokestyle smokestyle){
        ResponseResult result=new ResponseResult();
        smokestyleService.gxSmokestyle(smokestyle);
        result.getInfo().put("message","更新成功");
        return result;
    }
}
