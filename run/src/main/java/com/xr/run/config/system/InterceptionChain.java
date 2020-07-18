//package com.xr.run.config.system;
//
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class InterceptionChain  {
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//
//        ServletRegistrationBean bean = new ServletRegistrationBean(new ,"/springHelloWorld/*");
//        bean.setLoadOnStartup(1);
//        bean.addInitParameter("message", "SpringHelloWorldServlet special message");
//        return bean;
//    }
//}
