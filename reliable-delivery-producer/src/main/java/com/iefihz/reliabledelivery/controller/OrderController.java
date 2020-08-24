package com.iefihz.reliabledelivery.controller;

import com.iefihz.reliabledelivery.entity.Order;
import com.iefihz.reliabledelivery.service.OrderService;
import com.iefihz.reliabledelivery.util.MessageIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/add")
    public Order add() {
        Order order = new Order();
        order.setDesc("订单描述。。。");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setMessageId(MessageIdGenerator.generateMessageId("order"));
        return orderService.add(order);
    }
}
