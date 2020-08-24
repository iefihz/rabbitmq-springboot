package com.iefihz.rabbitmq.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 这种是work模型的轮询分发，公平分发需要另外配置：spring.rabbitmq.listener.simple.prefetch=1
 */
@Component
public class WorkConsumer {

    @RabbitListener(queuesToDeclare = @Queue(name = "work-springboot", durable = "true", exclusive = "false", autoDelete = "false"))
    public void consume01(String message) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[WorkConsumer-consume01]: " + message);
    }

    @RabbitListener(queuesToDeclare = @Queue(name = "work-springboot", durable = "true", exclusive = "false", autoDelete = "false"))
    public void consume02(String message) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[WorkConsumer-consume02]: " + message);
    }
}
