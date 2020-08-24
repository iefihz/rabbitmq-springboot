package com.iefihz.reliabledelivery.dao;

import com.iefihz.reliabledelivery.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {

    void add(Order order);
}
