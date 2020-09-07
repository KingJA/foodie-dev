package com.imooc.controller;


import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.ApiResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "购物车接口", tags = "购物车接口")
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @ApiOperation(value = "加入购物车", notes = "加入购物车", httpMethod = "POST")
    @PostMapping("add")
    public ApiResult add(@ApiParam(value = "商品Id", required = true, name = "userId") @RequestParam String userId,
                         @RequestBody ShopcartBO shopcartBO,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return ApiResult.errorMsg("用户不存在");
        }
        //前端用户在登录的情况下，添加商品到购物车，会同时在后台同步购物车到redis
        System.out.println(shopcartBO.toString());
        return ApiResult.ok();
    }

}
