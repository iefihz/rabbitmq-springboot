package com.iefihz.rabbitmq.producer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue(name = "simple-springboot", durable = "true", exclusive = "false", autoDelete = "false"))
public class SimpleConsumer {

    @RabbitHandler
    public void consume(String message) {
        System.out.println("[SimpleConsumer]: " + message);
    }
}
