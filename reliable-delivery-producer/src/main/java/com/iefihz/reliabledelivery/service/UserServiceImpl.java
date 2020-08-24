package com.iefihz.reliabledelivery.service;

import com.iefihz.reliabledelivery.entity.MessageContainer;
import com.iefihz.reliabledelivery.entity.User;
import com.iefihz.reliabledelivery.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BrokerMessageLogService brokerMessageLogService;

    @Autowired
    private RabbitSender rabbitSender;

    @Override
    public <T> User add(MessageContainer<T> container) {

        //1.user入库：略

        //2.消息入库
        brokerMessageLogService.addMessageContainer(container);

        //3.投递消息
        rabbitSender.send(container);


        return (User) container.getBody();
    }
}
