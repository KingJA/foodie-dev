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
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
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
        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(submitOrderBO.getUserId());
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(submitOrderBO.getPayMethod());

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
}

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus os : list) {
            // 获得订单创建时间
            Date createdTime = os.getCreatedTime();
            // 和当前时间进行对比
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                // 超过1天，关闭订单
                doCloseOrder(os.getOrderId());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
