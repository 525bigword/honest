package com.xr.run.config.fileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
  * 设置虚拟路径，访问绝对路径下资源
  * @author Administrator
  *
  */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {
    @Value("${file.staticAccessPaths}")
    private String staticAccessPath;
    @Value("${file.uploadFolders}")
    private String uploadFolder;
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String format = sdf.format(new Date());
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder) ;
    }

}
