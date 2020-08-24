package com.iefihz.reliabledelivery.controller;

import com.iefihz.reliabledelivery.entity.Book;
import com.iefihz.reliabledelivery.entity.MessageContainer;
import com.iefihz.reliabledelivery.entity.User;
import com.iefihz.reliabledelivery.service.BookService;
import com.iefihz.reliabledelivery.service.BrokerMessageLogService;
import com.iefihz.reliabledelivery.service.UserService;
import com.iefihz.reliabledelivery.util.MessageIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BrokerMessageLogService brokerMessageLogService;

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public void add() {
        Book book = new Book();
        book.setId(1L);
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        book.setName("水浒传");
        book.setMessageId(MessageIdGenerator.generateMessageId("book"));
        bookService.add(book);
    }

}
