package com.xr.run.config.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class CORSConfiguration {
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //用户是否可以发送、处理cookie
        config.setAllowCredentials(true);
        //允许该请求访问
        //config.addAllowedOrigin("http://localhost:9527/api");
        //config.addAllowedOrigin("http://localhost:9527/honest_war");
        config.addAllowedOrigin("http://192.168.21.1:9527");
        config.addAllowedOrigin("http://localhost:4031");
        config.addAllowedOrigin("*");
        config.addAllowedOrigin("http://127.0.0.1:4031");
        config.addAllowedOrigin("null");
        config.addAllowedHeader("*");//服务器允许使用的字段
        config.addAllowedMethod("*");//真实请求允许的方法
        source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
