package com.imooc.controller;

import com.imooc.service.StuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Object getStu(int id) {
        return stuService.getStuInfo(id);
    }
}
