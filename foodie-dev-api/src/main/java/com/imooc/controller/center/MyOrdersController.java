package com.imooc.controller.center;


import com.imooc.controller.BaseController;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.center.MyOrderService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "POST")
    @PostMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单ID不能为空");
        }
        myOrderService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public ApiResult confirmReceive(@ApiParam(value = "订单Id", required = true, name = "orderId") @RequestParam String orderId,
                                    @ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId
    ) {
        ApiResult result = checkUserOrder(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {
            return result;
        }

        boolean b = myOrderService.updateReceiveOrderStatus(orderId);
        if (!b) {
            return ApiResult.errorMsg("确认收获失败");
        }

        return ApiResult.ok();
    }

    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public ApiResult getComments(@ApiParam(value = "订单Id", required = true, name = "orderId") @RequestParam String orderId,
                                 @ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId
    ) {
        ApiResult result = checkUserOrder(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {
            return result;
        }

        boolean b = myOrderService.deleteOrder(userId, orderId);
        if (!b) {
            return ApiResult.errorMsg("删除订单失败");
        }

        return ApiResult.ok();
    }


    @ApiOperation(value = "各状态订单数", notes = "各状态订单数", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public ApiResult statusCounts(@ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId
    ) {
        return ApiResult.ok(myOrderService.getMyOrderStatusCounts(userId));
    }

    @ApiOperation(value = "订单动向", notes = "订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public ApiResult trend(@ApiParam(value = "用户Id", required = true, name = "userId") @RequestParam String userId,
                                 @ApiParam(value = "页码", required = true, name = "page") @RequestParam Integer page,
                                 @ApiParam(value = "每页数量", required = true, name = "pageSize") @RequestParam Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pageComments = myOrderService.getMyOrderTend(userId, page, pageSize);
        return ApiResult.ok(pageComments);
    }
}
