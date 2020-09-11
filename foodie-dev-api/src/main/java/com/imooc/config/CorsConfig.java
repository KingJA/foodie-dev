package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Description:TODO
 * Create Time:2020/9/3 0003 下午 2:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Configuration
public class CorsConfig {
    public CorsConfig() {

    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://49.232.232.173:8080");
        /*允许携带cookie*/
        config.setAllowCredentials(true);
        /*允许携带所有请求头*/
        config.addAllowedHeader("*");
        /*允许所有请求方法*/
        config.addAllowedMethod("*");
        /*添加映射路径*/
        UrlBasedCorsConfigurationSource CorsSource = new UrlBasedCorsConfigurationSource();
        CorsSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(CorsSource);
    }
}
