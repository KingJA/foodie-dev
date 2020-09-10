package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface MyCommentsService {
    List<OrderItems> queryPendComment(String orderId);

    void saveList(String orderId, String userId, List<OrderItemsCommentBO> commentList);
}
