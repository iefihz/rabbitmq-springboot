package com.iefihz.reliabledelivery.consumer;

import com.alibaba.fastjson.JSON;
import com.iefihz.reliabledelivery.dao.UniqueMessageIdMapper;
import com.iefihz.reliabledelivery.entity.Book;
import com.iefihz.reliabledelivery.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消费者方法的参数可以使用
 * org.springframework.messaging.Message和com.rabbitmq.client.Channel
 * 或者
 * @Payload 指定实体类 和 @Headers指定头（这两个相当于第一种的Message，可以查看源码）再加上com.rabbitmq.client.Channel
 */
@Component
public class RabbitReceiver {

    @Autowired
    private UniqueMessageIdMapper uniqueMessageIdMapper;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(
                            name = "reliable-delivery-queue-string",
                            durable = Exchange.TRUE,
                            exclusive = Exchange.FALSE,
                            autoDelete = Exchange.FALSE
                    ),
                    exchange = @Exchange(
                            name = "reliable-delivery-exchange-string",
                            type = ExchangeTypes.TOPIC,
                            durable = Exchange.TRUE,
                            autoDelete = Exchange.FALSE,
                            internal = Exchange.FALSE
                    ),
                    key = {
                            "string.#"
                    }
            )
    })
    public void onStringMessage(Message message, Channel channel) {
        long deliveryTag = (long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        try {
            String msg = (String) message.getPayload();
            System.out.println("[DeliveryTag string]: " + deliveryTag);
            System.out.println("[OrderConsumer string]: " + msg);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.out.println("=======消费者ack产生异常========");
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("=======消费者Nack产生异常========");
            }
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(
                            name = "reliable-delivery-queue",
                            durable = Exchange.TRUE,
                            exclusive = Exchange.FALSE,
                            autoDelete = Exchange.FALSE
                    ),
                    exchange = @Exchange(
                            name = "reliable-delivery-exchange",
                            type = ExchangeTypes.TOPIC,
                            durable = Exchange.TRUE,
                            autoDelete = Exchange.FALSE,
                            internal = Exchange.FALSE
                    ),
                    key = {
                            "order.#"
                    }
            )
    })
    public void onOrderMessage(
            @Payload com.iefihz.reliabledelivery.entity.Order order,
            @Headers Map<String, Object> headers,
            Channel channel) throws IOException {
        long deliveryTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);

        //幂等性操作
        try {
            uniqueMessageIdMapper.add(order.getMessageId());
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                channel.basicAck(deliveryTag, false);       //说明这个消息是重复的消息，直接确认即可
            } else {
                channel.basicNack(deliveryTag, false, false);       //其他数据库错误，需要异常检查。。。
            }
            return;
        }

        System.out.println("真正的消息处理...");
        //真正的消息处理...
        try {
            String msg = JSON.toJSONString(order);
            System.out.println("[DeliveryTag order]: " + deliveryTag);
            System.out.println("[OrderConsumer order]: " + msg);

            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.out.println("=======消费者ack产生异常========");
            channel.basicNack(deliveryTag, false, false);
        }

    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(
                            name = "reliable-delivery-queue-container",
                            durable = Exchange.TRUE,
                            exclusive = Exchange.FALSE,
                            autoDelete = Exchange.FALSE
                    ),
                    exchange = @Exchange(
                            name = "reliable-delivery-exchange-container",
                            type = ExchangeTypes.TOPIC,
                            durable = Exchange.TRUE,
                            autoDelete = Exchange.FALSE,
                            internal = Exchange.FALSE
                    ),
                    key = {
                            "container.#"
                    }
            )
    })
    public void onContainerMessage(
            @Payload com.iefihz.reliabledelivery.entity.MessageContainer<User> container,
            @Headers Map<String, Object> headers,
            Channel channel) {
        long deliveryTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            String cmsg = JSON.toJSONString(container);
            User user = container.getBody();
            System.out.println("[DeliveryTag order]: " + deliveryTag);
            System.out.println("[OrderConsumer order]: " + cmsg);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.out.println("=======消费者ack产生异常========");
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("=======消费者Nack产生异常========");
            }
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(
                            name = "reliable-delivery-queue-base",
                            durable = Exchange.TRUE,
                            exclusive = Exchange.FALSE,
                            autoDelete = Exchange.FALSE
                    ),
                    exchange = @Exchange(
                            name = "reliable-delivery-exchange-base",
                            type = ExchangeTypes.TOPIC,
                            durable = Exchange.TRUE,
                            autoDelete = Exchange.FALSE,
                            internal = Exchange.FALSE
                    ),
                    key = {
                            "base.#"
                    }
            )
    })
    public void onBookBaseMessage(
            @Payload Book book,
            @Headers Map<String, Object> headers,
            Channel channel) {
        long deliveryTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            String cmsg = JSON.toJSONString(book);
            System.out.println("[DeliveryTag book]: " + deliveryTag);
            System.out.println("[OrderConsumer book]: " + cmsg);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            System.out.println("=======消费者ack产生异常========");
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("=======消费者Nack产生异常========");
            }
            e.printStackTrace();
        }
    }

}
