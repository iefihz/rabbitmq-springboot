package com.iefihz.reliabledelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableTransactionManagement          //SpringBoot中，可以不使用这个注解开启事务，传统的spring项目需要
//@MapperScan(basePackages = "com.iefihz.reliabledelivery.dao")         //此配置移动到com.iefihz.reliabledelivery.config.DruidDataSourceConfig类上了
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
