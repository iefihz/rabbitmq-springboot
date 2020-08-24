package com.iefihz.rabbitmq.routing;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //临时队列，不指定名称
                    exchange = @Exchange(value = "routing-exchange-springboot", type = "direct"),
                    key = {"info", "error"}
            )
    })
    public void consumer01(String message) {
        System.out.println("[RoutingConsumer-consumer01]: " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //临时队列，不指定名称
                    exchange = @Exchange(value = "routing-exchange-springboot", type = "direct"),
                    key = {"error"}
            )
    })
    public void consumer02(String message) {
        System.out.println("[RoutingConsumer-consumer02]: " + message);
    }
}
