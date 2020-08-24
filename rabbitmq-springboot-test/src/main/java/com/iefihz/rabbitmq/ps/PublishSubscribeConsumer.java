package com.iefihz.rabbitmq.ps;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PublishSubscribeConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //不指定队列名称则为临时队列
                    exchange = @Exchange(name = "ps-exchange-springboot", type = "fanout")
            )
    })
    public void consumer01(String message) {
        System.out.println("[PublishSubscribeConsumer-consumer01]: " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //不指定队列名称则为临时队列
                    exchange = @Exchange(name = "ps-exchange-springboot", type = "fanout")
            )
    })
    public void consumer02(String message) {
        System.out.println("[PublishSubscribeConsumer-consumer02]: " + message);
    }
}
