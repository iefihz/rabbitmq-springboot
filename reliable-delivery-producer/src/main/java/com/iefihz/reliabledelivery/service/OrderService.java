package com.iefihz.reliabledelivery.service;

import com.iefihz.reliabledelivery.entity.Order;

public interface OrderService {

    /**
     * 订单添加
     * @param order
     * @return 持久化的订单
     */
    Order add(Order order);
}
