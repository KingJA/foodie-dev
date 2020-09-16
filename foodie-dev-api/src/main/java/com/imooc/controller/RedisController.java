package com.imooc.controller;

import com.imooc.utils.RedisOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("redis")
@Log
public class RedisController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisOperator  redisOperator;

    @GetMapping("/set")
    public Object hello(String key,String value) {
//        redisTemplate.opsForValue().set(key,value);
        redisOperator.set(key,value);
        return "ok";
    }
    @GetMapping("/get")
    public Object get(String key) {
//        return redisTemplate.opsForValue().get(key);
        return redisOperator.get(key);
    }
    @GetMapping("/delete")
    public Object delete(String key) {
//        redisTemplate.delete(key);
        redisOperator.del(key);
        return "ok";
    }
}
