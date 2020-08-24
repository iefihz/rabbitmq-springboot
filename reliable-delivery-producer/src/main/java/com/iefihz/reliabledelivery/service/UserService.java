package com.iefihz.reliabledelivery.service;

import com.iefihz.reliabledelivery.entity.MessageContainer;
import com.iefihz.reliabledelivery.entity.User;

public interface UserService {

    /**
     * 订单添加
     * @return 持久化的订单
     */
    <T> User add(MessageContainer<T> container);
}
