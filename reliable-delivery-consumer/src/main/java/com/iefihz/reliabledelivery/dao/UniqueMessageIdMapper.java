package com.iefihz.reliabledelivery.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UniqueMessageIdMapper {
    void add(@Param("messageId") String messageId);
}
