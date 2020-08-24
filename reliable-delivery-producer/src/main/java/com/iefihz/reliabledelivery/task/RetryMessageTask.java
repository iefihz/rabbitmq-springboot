package com.iefihz.reliabledelivery.task;

import com.alibaba.fastjson.JSON;
import com.iefihz.reliabledelivery.constant.MessageStatus;
import com.iefihz.reliabledelivery.dao.BrokerMessageLogMapper;
import com.iefihz.reliabledelivery.entity.BaseMessage;
import com.iefihz.reliabledelivery.entity.BrokerMessageLog;
import com.iefihz.reliabledelivery.entity.MessageContainer;
import com.iefihz.reliabledelivery.entity.Order;
import com.iefihz.reliabledelivery.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 定时任务，目的为了处理ack为false以及没收到ack的消息
 */
@Component
public class RetryMessageTask {

	@Autowired
	private RabbitSender rabbitSender;
	
	@Autowired
	private BrokerMessageLogMapper brokerMessageLogMapper;

	/**
	 * 第一次延迟5s开始定时任务，后续执行完一个任务后，隔10s执行下一次任务
	 */
	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void reSend() {
		System.err.println("---------------定时任务开始---------------");
		//获取正在发送，且发送超时的消息
		List<BrokerMessageLog> list = brokerMessageLogMapper.queryMessageOnSendingAndTimeout();
		list.forEach(messageLog -> {
			if(messageLog.getTryCount() >= 3) {
				//重发3次及以上，仍然没收到ack的视为失败
				brokerMessageLogMapper.updateStatus(messageLog.getId(), MessageStatus.SEND_FAILURE, new Date());
			} else {
				//重发
				System.err.println("===========消息重发===========");
				brokerMessageLogMapper.update4ReSend(messageLog.getId(),  new Date());
				String message = messageLog.getMessage();
				System.out.println("message: " + message);
				try {

					Order reSendOrder = JSON.parseObject(message, Order.class);
					rabbitSender.sendOrderMessage(reSendOrder);


//					MessageContainer container = JSON.parseObject(message, MessageContainer.class);
//					rabbitSender.send(container);


//					BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
//					rabbitSender.sendBaseMessage(baseMessage);


				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("===========异常处理===========");
				}
			}			
		});
		
	}
	
	
}
