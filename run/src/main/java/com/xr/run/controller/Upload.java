package com.xr.run.controller;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
    @Value("${file.uploadDuty}")
    private String dutyPath;
    @Value("${file.uploadVideo}")
    private String vidoPath;
    @Value("${file.uploadImage}")
    private String imagePth;
    @PostMapping("/import")
    public JSONObject importData(@RequestParam("file")MultipartFile file, HttpServletRequest req) throws IOException {
        Map filePath = getFilePath(file, req, accessPath, realBasePath);
        return CommonUtil.successJson(filePath);
    }
    @RequestMapping("/importDuty")
    public JSONObject importDuty(@RequestParam("file")MultipartFile file, HttpServletRequest req) throws IOException {
        Map filePath = getFilePath(file, req, accessPath, dutyPath);
        return CommonUtil.successJson(filePath);
    }

    @RequestMapping("/image")
    public JSONObject importImage(@RequestParam("file")MultipartFile file, HttpServletRequest req) throws IOException {
        Map filePath = getFilePath(file, req, accessPath, imagePth);
        return CommonUtil.successJson(filePath);
    }

    @RequestMapping("/vido")
    public JSONObject uploadfile(@RequestParam("file")MultipartFile file, HttpServletRequest req) throws IOException {
        System.out.println("anndfs===========");
        Map filePath = getFilePath(file, req, accessPath, vidoPath);
        return CommonUtil.successJson(filePath);
    }


    public Map getFilePath(MultipartFile file, HttpServletRequest req, String netPath, String thisPath)throws IOException{
        String format = sdf.format(new Date());
        // 域名访问的相对路径（通过浏览器访问的链接-虚拟路径）
        String saveToPath = netPath + format;
        // 真实路径，实际储存的路径
        String realPath = thisPath + format;
        // 储存文件的物理路径，使用本地路径储存
        System.out.println("--虚拟文件路径为：" + saveToPath +"--物理文件路径为：" + realPath);
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + "8080" + saveToPath + newName;//127.0.0.1:8080/uploadimg/...
        System.out.println(url);
        Map<String,String> map=new HashMap<>();
        map.put("dFile",format + newName);
        return map;
    }

    public static void writeFile(MultipartFile multipartFile,String path,String fileName) throws FileNotFoundException {
        File dir = new File(path);
        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
        //生成文件
        File vidofile = new File(dir, fileName);
        //传输内容
        try {
            multipartFile.transferTo(vidofile);
            System.out.println(File.pathSeparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
