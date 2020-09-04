package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.service.impl.CarouselService;
import com.imooc.utils.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "首页接口", tags = "首页展示相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @ApiOperation(value = "判断用户名是否存在", notes = "用户名是否存在notes", httpMethod = "GET")
    @GetMapping("/carousel")
    public ApiResult queryAllCarousel() {
        List<Carousel> resultList = carouselService.queryAll(YesOrNo.YES.type);
        return ApiResult.ok(resultList);
    }

    /**
     * 首页分类加载需求
     * 1.第一次加载全部一级分类
     * 2.鼠标移动到一级分类后，加载对应的子分类的内容，如果加载过的则不需要再加载(懒加载)
     */

}
