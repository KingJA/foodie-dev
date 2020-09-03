package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description:TODO
 * Create Time:2020/9/3 0003 上午 10:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Configuration
@EnableSwagger2
public class Swagger2 {


    //预览网址：http://localhost:8088/swagger-ui.html
    //bootstrap 风格 预览网址：http://localhost:8088/doc.html
    @Bean
    public Docket createSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imooc.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("API文档标题")
                .contact(new Contact("KingJA","https://kblue.tech","kingjavip@gmail.com"))
                .description("测试的api文档描述")
                .version("1.0.0")
                .termsOfServiceUrl("https://kblue.tech")
                .build();
    }
}
