package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/imp")
public class Upload {
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @Value("${file.uploadFolder}")
    private String realBasePath;
    @Value("${file.accessPath}")
    private String accessPath;
    @PostMapping("/import")
    public JSONObject importData(MultipartFile file, HttpServletRequest req) throws IOException {
        String format = sdf.format(new Date());
        // 域名访问的相对路径（通过浏览器访问的链接-虚拟路径）
        String saveToPath = accessPath + format;
        // 真实路径，实际储存的路径
        String realPath = realBasePath + format;
        // 储存文件的物理路径，使用本地路径储存
        System.out.println("--虚拟文件路径为：" + saveToPath +"--物理文件路径为：" + realPath);
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + "8080" + saveToPath + newName;
        Map<String,String> map=new HashMap<>();
        map.put("dFile",saveToPath + newName);
        return CommonUtil.successJson(map);
    }
}
