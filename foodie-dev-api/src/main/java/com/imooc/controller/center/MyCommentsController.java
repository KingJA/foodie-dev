package com.imooc.controller.center;


import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.ApiResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(value = "我的评论", tags = "我的评论")
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "POST")
    @PostMapping("/pending")
    public ApiResult pending(@ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId,
                             @ApiParam(value = "用户Id", required = true, name = "orderId") @RequestParam String orderId) {
        //1.判断用户和订单是否关联

        ApiResult apiResult = checkUserOrder(userId, orderId);
        if (apiResult.getStatus() != HttpStatus.OK.value()) {
            return apiResult;
        }
        //2.判断订单是否评价过

        Orders orders = (Orders) apiResult.getData();
        if (orders.getIsComment().equals(YesOrNo.YES.type)) {
            return ApiResult.errorMsg("订单已经评价过");
        }
        return ApiResult.ok(myCommentsService.queryPendComment(orderId));
    }

    @ApiOperation(value = "商品评价", notes = "商品评价", httpMethod = "POST")
    @PostMapping("/saveList")
    public ApiResult saveList(@ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId,
                              @ApiParam(value = "用户Id", required = true, name = "orderId") @RequestParam String orderId,
                              @RequestBody List<OrderItemsCommentBO> commentList) {
        System.out.println(commentList.toString());
        //1.判断用户和订单是否关联
        ApiResult apiResult = checkUserOrder(userId, orderId);
        if (apiResult.getStatus() != HttpStatus.OK.value()) {
            return apiResult;
        }

        if (commentList.isEmpty()) {
            return ApiResult.errorMsg("评价内容不能为空");
        }

        myCommentsService.saveList(orderId,userId,commentList);
        return ApiResult.ok();
    }
}
