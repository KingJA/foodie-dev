package com.imooc.controller;


import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.service.ItemService;
import com.imooc.utils.ApiResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(value = "商品接口", tags = "商品接口")
@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "获取商品详情", notes = "获取商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public ApiResult getRecommendGoodsList(@ApiParam(value = "商品Id", required = true, name = "itemId")
                                           @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ApiResult.errorMsg("商品不存在");

        }
        Items item = itemService.getItemDetail(itemId);
        List<ItemsImg> itemImgList = itemService.getItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.getItemSpecList(itemId);
        ItemsParam itemParam = itemService.getItemParam(itemId);
        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemImgList);
        itemInfoVo.setItemSpecList(itemSpecList);
        itemInfoVo.setItemParams(itemParam);
        return ApiResult.ok(itemInfoVo);
    }

    @ApiOperation(value = "获取商品各等级评价数量", notes = "获取商品各等级评价数量", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public ApiResult getCommentLevelCounts(@ApiParam(value = "商品Id", required = true, name = "itemId")
                                           @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ApiResult.errorMsg("评价等级不存在");
        }
        return ApiResult.ok(itemService.getCommentCounts(itemId));
    }
}
