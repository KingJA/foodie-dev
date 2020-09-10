package com.imooc.service.center;

import com.imooc.pojo.Orders;
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

    /**
     * 订单状态-->商家发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);


    /**
     * 更新订单状态--> 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单(逻辑删除)
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId,String orderId);

    /**
     * 查询我的订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);
}
