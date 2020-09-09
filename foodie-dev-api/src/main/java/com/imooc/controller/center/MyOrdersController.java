package com.imooc.controller.center;


import com.imooc.controller.BaseController;
import com.imooc.service.center.MyOrderService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.PagedGridResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "用户中心-我的订单", tags = "用户中心-我的订单")
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrderService myOrderService;


    @ApiOperation(value = "我的订单", notes = "我的订单", httpMethod = "POST")
    @PostMapping("/query")
    public ApiResult getComments(@ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId,
                                 @ApiParam(value = "订单状态", required = false, name = "orderStatus") @RequestParam Integer orderStatus,
                                 @ApiParam(value = "页码", required = true, name = "page") @RequestParam Integer page,
                                 @ApiParam(value = "每页数量", required = true, name = "pageSize") @RequestParam Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pageComments = myOrderService.getMyOrders(userId, orderStatus, page, pageSize);
        return ApiResult.ok(pageComments);
    }
}
