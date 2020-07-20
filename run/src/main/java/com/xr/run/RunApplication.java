package com.xr.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.xr.run.dao")
public class RunApplication {
    @Resource
    private Environment environment;
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }

    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver){
        if(viewResolver!=null){
            Map<String,Object> vars=new HashMap<>();
            vars.put("html",environment.getProperty("html"));
            vars.put("visitbackstage",environment.getProperty("visitbackstage"));
            vars.put("staticfile",environment.getProperty("staticfile"));
            viewResolver.setStaticVariables(vars);
        }
    }

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(RunApplication.class);extends SpringBootServletInitializer
    }*/

}
