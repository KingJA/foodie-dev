package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrdersMapper ordersMapper;
    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;


    @Autowired
    public Sid sid;

    @Autowired
    public AddressService addressService;

    @Autowired
    public ItemService itemService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createOrder(SubmitOrderBO submitOrderBO) {
        //1.订单表
        Orders orders = new Orders();
        String orderId = sid.nextShort();
        UserAddress userAddress = addressService.queryAddress(submitOrderBO.getUserId(), submitOrderBO.getAddressId());
        Integer postAmount = 0;
        orders.setId(orderId);
        orders.setUserId(submitOrderBO.getUserId());
        orders.setLeftMsg(submitOrderBO.getLeftMsg());
        orders.setPayMethod(submitOrderBO.getPayMethod());
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());

        orders.setPostAmount(postAmount);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setReceiverAddress(userAddress.getProvince()
                + " " + userAddress.getCity()
                + " " + userAddress.getDistrict()
                + " " + userAddress.getDistrict());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverName(userAddress.getReceiver());
        //2.订单商品关联表
        int totalAmount = 0;
        int realPayAmount = 0;
        Integer buyCounts = 1;
        String[] itemSpecIdAttr = submitOrderBO.getItemSpecIds().split(",");
        for (String specId : itemSpecIdAttr) {
            // 2.1根据规格id获取规格信息，获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecBySpecId(specId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            // 2.2根据规格id获取商品信息，名称，主图
            String itemId = itemsSpec.getItemId();
            Items items = itemService.getItemDetail(itemId);
            String itemUrl = itemService.getItemMainImgById(items.getId());
            // 2.3保存订单商品关联表到数据库
            OrderItems orderItems = new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setBuyCounts(buyCounts);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(itemUrl);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemSpecId(specId);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setOrderId(orderId);
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);
            // 用户提交订单后，需要扣除库存

            itemService.decreaseStock(specId, buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);
        //TODO 整合redis后，商品购买的数量从redis中获取
        //3.订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);
        return orderId;
    }
}
