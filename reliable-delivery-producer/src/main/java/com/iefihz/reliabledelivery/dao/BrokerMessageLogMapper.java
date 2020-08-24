package com.iefihz.reliabledelivery.dao;

import com.iefihz.reliabledelivery.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BrokerMessageLogMapper {

    void add(BrokerMessageLog log);

    void updateStatus(@Param("id") String id, @Param("status") String status, @Param("updateTime") Date updateTime);

    List<BrokerMessageLog> queryMessageOnSendingAndTimeout();

    void update4ReSend(@Param("id") String id, @Param("updateTime") Date updateTime);
}
