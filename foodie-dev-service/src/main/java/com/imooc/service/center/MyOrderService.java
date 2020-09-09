package com.imooc.service.center;

import com.imooc.utils.PagedGridResult;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface MyOrderService {
    /**
     * 查询我的订单
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult getMyOrders(String userId, Integer orderStatus , int page, int pageSize);
}
