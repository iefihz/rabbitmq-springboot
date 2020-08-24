package com.iefihz.reliabledelivery.callbackconfigurator;

import com.iefihz.reliabledelivery.constant.MessageStatus;
import com.iefihz.reliabledelivery.dao.BrokerMessageLogMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  实现RabbitTemplate中的两个回调方法confirmCallback、returnCallback
 */
@Component
public class RabbitSenderCallbackConfigurator {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                //收到ack为true，消息记录状态更改为已发送
                brokerMessageLogMapper.updateStatus(messageId, MessageStatus.SEND_SUCCESS, new Date());
                System.out.println("========ack: true=======");
//                System.out.println("========ack: true，但不改变消息状态，测试重发=======");
            } else {
                //收到ack为false，定时任务进行重发处理
                System.err.println("========ack: false，进行异常处理=======");
            }
        }
    };

    private final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("==========ReturnCallback=========");
            System.out.println("message: " + new String(message.getBody()));
            System.out.println("replyCode: " + replyCode);
            System.out.println("replyText: " + replyText);
            System.out.println("exchange: " + exchange);
            System.out.println("routingKey: " + routingKey);
        }
    };

    public void initCallback() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
    }

}
