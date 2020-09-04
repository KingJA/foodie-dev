package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.java.Log;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@ApiIgnore
@RestController
@Log
public class HelloController {
    private final Logger logger= LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {
        log.info("log log日志信息");
        logger.debug("logger log日志信息");
        logger.error("logger log日志信息");
        logger.info("logger log日志信息");
        logger.warn("logger log日志信息");
        return "hello world";
    }


    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userInfo","new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        return "ok";
    }
}
