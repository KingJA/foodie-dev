package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.RecommendCatsVo;
import com.imooc.service.CategoryService;
import com.imooc.service.CarouselService;
import com.imooc.utils.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

    @Autowired
    private CategoryService categoryService;

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


    @ApiOperation(value = "获取所有一级列表", notes = "获取所有一级列表", httpMethod = "GET")
    @GetMapping("/cats")
    public ApiResult getAllRootLevelCategory() {
        List<Category> resultList = categoryService.getAllRootLevelCategory();
        return ApiResult.ok(resultList);
    }

    @ApiOperation(value = "获取子分类列表", notes = "获取子分类列表", httpMethod = "GET")
    @GetMapping("/subCat/{rootCateId}")
    public ApiResult getSubCategory(@ApiParam(value = "一级分类id", required = true, name = "rootCateId") @PathVariable Integer rootCateId) {
        if (rootCateId == null) {
            return ApiResult.errorMsg("分类信息不存在");

        }
        List<CategoryVo> resultList = categoryService.getSubCategoryList(rootCateId);
        return ApiResult.ok(resultList);
    }


    @ApiOperation(value = "获取推荐列表", notes = "获取推荐列表", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCateId}")
    public ApiResult getRecommendGoodsList(@ApiParam(value = "一级分类id", required = true, name = "rootCateId") @PathVariable Integer rootCateId) {
        if (rootCateId == null) {
            return ApiResult.errorMsg("推荐列表不存在");

        }
        List<RecommendCatsVo> resultList = categoryService.getRecommendGoodsList(rootCateId);
        return ApiResult.ok(resultList);
    }
}
