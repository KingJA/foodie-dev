package com.imooc.mapper;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.vo.MyOrdersVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {
    List<MyOrdersVO> getMyOrders(@Param("paramsMap") Map map);

    int getMyOrderStatusCounts(@Param("paramsMap") Map map);

    List<OrderStatus> getMyOrderTend(@Param("paramsMap") Map map);
}