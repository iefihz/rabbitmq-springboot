package com.iefihz.reliabledelivery.producer;

import com.iefihz.reliabledelivery.callbackconfigurator.RabbitSenderCallbackConfigurator;
import com.iefihz.reliabledelivery.entity.BaseMessage;
import com.iefihz.reliabledelivery.entity.MessageContainer;
import com.iefihz.reliabledelivery.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitSenderCallbackConfigurator callbackConfigurator;

//    public void sendStringMessage(String message) {
//        callbackConfigurator.initCallback();
//        rabbitTemplate.convertAndSend("", "", message, );
//    }

    public void sendOrderMessage(Order order) {
        callbackConfigurator.initCallback();          //实现RabbitTemplate中的两个回调方法confirmCallback、returnCallback
        rabbitTemplate.convertAndSend("reliable-delivery-exchange", "order.add",
                order, new CorrelationData(order.getMessageId()));
    }

    public void send(MessageContainer container) {
        callbackConfigurator.initCallback();          //实现RabbitTemplate中的两个回调方法confirmCallback、returnCallback
        rabbitTemplate.convertAndSend("reliable-delivery-exchange-container", "container.add",
                container, new CorrelationData(container.getMessageId()));
    }

    public void sendBaseMessage(BaseMessage message) {
        callbackConfigurator.initCallback();          //实现RabbitTemplate中的两个回调方法confirmCallback、returnCallback
        rabbitTemplate.convertAndSend("reliable-delivery-exchange-base", "base.add",
                message, new CorrelationData(message.getMessageId()));
    }

}
