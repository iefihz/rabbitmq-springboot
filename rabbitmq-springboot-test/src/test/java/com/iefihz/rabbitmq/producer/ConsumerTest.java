package com.iefihz.rabbitmq.producer;

import com.iefihz.rabbitmq.RabbitmqSpringbootTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 模拟生产者
 */
@SpringBootTest(classes = RabbitmqSpringbootTestApplication.class)
@RunWith(SpringRunner.class)
public class ConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimple() {
        rabbitTemplate.convertAndSend("simple-springboot", "hello simple!");
    }

    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work-springboot", "hello work!" + i);
        }
    }

    @Test
    public void testPublishSubscribe() {
        rabbitTemplate.convertAndSend("ps-exchange-springboot", "", "hello Publish/Subscribe!");
    }

    @Test
    public void testRouting() {
        rabbitTemplate.convertAndSend("routing-exchange-springboot", "info", "hello routing! - info");
        rabbitTemplate.convertAndSend("routing-exchange-springboot", "error", "hello routing! - error");
    }

    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topic-exchange-springboot", "goods.add", "hello topic! - good.add");
        rabbitTemplate.convertAndSend("topic-exchange-springboot", "goods.add.add", "hello topic! - good.add.add");
        rabbitTemplate.convertAndSend("topic-exchange-springboot", "user.add", "hello topic! - user.add");
    }
}
