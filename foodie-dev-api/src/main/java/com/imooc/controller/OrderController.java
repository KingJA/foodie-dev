package com.imooc.controller;


import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.AddressService;
import com.imooc.service.OrderService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.CookieUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Description:TODO
 * Create Time:2020/8/30 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Api(value = "订单", tags = "订单")
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "提交订单", notes = "提交订单", httpMethod = "POST")
    @PostMapping("/create")
    public ApiResult create(@RequestBody SubmitOrderBO submitOrderBO,
                            HttpServletRequest request, HttpServletResponse response) {
        Integer payMethod = submitOrderBO.getPayMethod();
        if (!payMethod.equals(PayMethod.WEIXIN.type) && !payMethod.equals(PayMethod.ALIPAY.type)) {
            return ApiResult.errorMsg("支付方式不支持");
        }


        //1.提交订单
        String orderId = orderService.createOrder(submitOrderBO);
        //2.提交订单后删除购物车中已经提交的商品

        //TODO 整合redis后，完善购物车已经结算的商品清楚，同时同步到前端的cookie
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);


        //3.向支付中心发送订单，用户保存支付中心的订单数据
        System.out.println(submitOrderBO.toString());
        return ApiResult.ok(orderId);
    }

}
