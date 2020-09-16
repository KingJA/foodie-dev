package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.RecommendCatsVo;
import com.imooc.service.CategoryService;
import com.imooc.service.CarouselService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 获取轮播图
     *
     * 更新缓存
     * 1. 后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2. 定时重置，比如每天凌晨三点重置
     * 3. 每个轮播图都有可能是一个广告，每个广告都会有一个过期时间，过期了，再重置
     */
    @ApiOperation(value = "判断用户名是否存在", notes = "用户名是否存在notes", httpMethod = "GET")
    @GetMapping("/carousel")
    public ApiResult queryAllCarousel() {
        String carousel = redisOperator.get("carousel");
        List<Carousel> resultList;
        if (StringUtils.isBlank(carousel)) {
            resultList = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(resultList));
        } else {
            resultList = JsonUtils.jsonToList(carousel, Carousel.class);
        }
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
        String result = redisOperator.get("cats");
        List<Category> resultList;
        if (StringUtils.isBlank(result)) {
            resultList = categoryService.getAllRootLevelCategory();
            redisOperator.set("cats", JsonUtils.objectToJson(resultList));
        } else {
            resultList = JsonUtils.jsonToList(result, Category.class);
        }
        return ApiResult.ok(resultList);
    }

    @ApiOperation(value = "获取子分类列表", notes = "获取子分类列表", httpMethod = "GET")
    @GetMapping("/subCat/{rootCateId}")
    public ApiResult getSubCategory(@ApiParam(value = "一级分类id", required = true, name = "rootCateId") @PathVariable Integer rootCateId) {
        if (rootCateId == null) {
            return ApiResult.errorMsg("分类信息不存在");

        }
        String result = redisOperator.get("subCat:" + rootCateId);
        List<CategoryVo> resultList;
        if (StringUtils.isBlank(result)) {
            resultList = categoryService.getSubCategoryList(rootCateId);
            /**
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (resultList != null && resultList.size() > 0) {
                redisOperator.set("subCat:" + rootCateId, JsonUtils.objectToJson(resultList));
            } else {
                redisOperator.set("subCat:" + rootCateId, JsonUtils.objectToJson(resultList), 5*60);
            }
        } else {
            resultList = JsonUtils.jsonToList(result, CategoryVo.class);
        }

        return ApiResult.ok(resultList);
    }


    @ApiOperation(value = "获取推荐列表", notes = "获取推荐列表", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCateId}")
    public ApiResult getRecommendGoodsList(@ApiParam(value = "一级分类id", required = true, name = "rootCateId")
                                           @PathVariable Integer rootCateId) {
        if (rootCateId == null) {
            return ApiResult.errorMsg("推荐列表不存在");

        }
        List<RecommendCatsVo> resultList = categoryService.getRecommendGoodsList(rootCateId);
        return ApiResult.ok(resultList);
    }
}
