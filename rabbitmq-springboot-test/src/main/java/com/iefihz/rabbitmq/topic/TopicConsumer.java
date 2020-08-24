package com.iefihz.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //不指定队列名称，临时队列
                    exchange = @Exchange(value = "topic-exchange-springboot", type = "topic"),
                    key = {"goods.*"}
            )
    })
    public void consumer01(String message) {
        System.out.println("[TopicConsumer-consumer01]: " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,     //不指定队列名称，临时队列
                    exchange = @Exchange(value = "topic-exchange-springboot", type = "topic"),
                    key = {"goods.#", "user.add"}
            )
    })
    public void consumer02(String message) {
        System.out.println("[TopicConsumer-consumer02]: " + message);
    }
}
