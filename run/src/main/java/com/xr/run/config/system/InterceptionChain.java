//package com.xr.run.config.system;
//
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
//public class InterceptionChain extends WebMvcConfigurationSupport {
//
//
//    /**
//     * 添加静态资源用的
//     * @info 2019-10-17 13:51 liuxi
//     *
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 增加一个"classpath:/templates/"即可访问templates下的静态资源
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "classpath:/public/", "classpath:/templates/");
//        super.addResourceHandlers(registry);
//    }
//
//
//    /**
//     *       添加拦截器用的，可以为每个不同的拦截器配置不同的拦截地址
//     * @info 2019-10-17 13:49 liuxi
//     *
//     * @param registry
//     */
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        // 过滤列表
//        List<String> excludePathList = new ArrayList<>();
//
//        super.addInterceptors(registry);
//    }
//}
