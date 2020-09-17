package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.center.MyOrderService;
import com.imooc.utils.ApiResult;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    @Autowired
    protected RedisOperator redisOperator;


    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";		// produce

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                       |-> 回调通知的url
//    String payReturnUrl = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";
//    String payReturnUrl = "http://vrjg7q.natappfree.cc/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://api.foodie.kblue.tech/orders/notifyMerchantOrderPaid";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
                                                            File.separator + "images" +
                                                            File.separator + "foodie" +
                                                            File.separator + "faces";
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie/faces";

    @Autowired
    public MyOrderService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     *
     * @return
     */
    public ApiResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return ApiResult.errorMsg("订单不存在！");
        }
        return ApiResult.ok(order);
    }
    public UsersVO getConventUserVO(Users result) {
        //生成token并按uiserId保存到redis,返回带有token的UserVo到cookie
        String token= UUID.randomUUID().toString();
        redisOperator.set(REDIS_USER_TOKEN+":"+result.getId(),token);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(result,usersVO);
        return usersVO;
    }

}
