package com.iefihz.reliabledelivery.service;

import com.iefihz.reliabledelivery.entity.Book;
import com.iefihz.reliabledelivery.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BrokerMessageLogService brokerMessageLogService;

    @Autowired
    private RabbitSender rabbitSender;

    @Override
    public Book add(Book book) {
        //1.book记录持久化

        //2.消息记录持久化
        brokerMessageLogService.addMessageLog(book);

        //3.投递消息
        rabbitSender.sendBaseMessage(book);

        return book;
    }
}
