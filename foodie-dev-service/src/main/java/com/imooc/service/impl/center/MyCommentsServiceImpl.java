package com.imooc.service.impl.center;

import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.center.MyCommentsService;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2020/9/1 0001 下午 4:37
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {


    @Autowired
    OrderItemsMapper orderItemsMapper;

    @Autowired
    ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void saveList(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        //1. 保存评价
        for (OrderItemsCommentBO comment : commentList) {
            comment.setCommentId(sid.nextShort());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);


        //2.订单 已评价
        //3.状态表 评价时间
    }
}
