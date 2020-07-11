package com.xr.run.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

@Service
@Slf4j
public class StaticHtmlService implements com.xr.run.service.StaticHtmlService {
    @Autowired
    private TemplateEngine templateEngine;//这是thymeleaf模板处理引擎

    @Autowired
    private ApplicationContext appContext;//这是Spring容器上下文
    @Value("${html.destPath}")
    private String destpath="";
    @Override
    public void genHtmlPage(String destpath,ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,String filename) {
        FileWriter fileWriter = null;
        try {
//            String fileName = request.getRequestURI();
//            System.out.println(fileName);
//            if(!fileName.endsWith(".html")) {//将.html结尾的请求生成静态页面
//                return;
//            }'
//            fileName=fileName.substring(0,fileName.lastIndexOf("."));
            String fileName = destpath+filename+".html";//config.getHtmlPath() + fileName;//构造静态html文件完整路径
            System.out.println(fileName);
            File file = new File(fileName);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fileWriter = new FileWriter(file);
            WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), modelAndView.getModelMap());
            templateEngine.process(modelAndView.getViewName(), context, fileWriter);//将thymeleaf模板生成的结果存入静态文件中
            log.info("已生成静态文件：" + fileName);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fileWriter);
        }

    }

    @Override
    public void deleteHtmlPage(String fileName) {
        try {
           // fileName = "D:\\IDE\\tomcat\\apache-tomcat-9.0.36\\webapps\\hoonest\\"+fileName+".html";//config.getHtmlPath() + fileName;
            File file = new File(fileName);
            //删除文件或目录
//           FileUtils.deleteDirectory(file);
            file.delete();
            log.info("已删除静态文件：" + fileName);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    //@Autowired
    //private ConfigProperties config;//这是工程中的配置属性，如静态文件的根目录/usr/local/nginx/html

}
