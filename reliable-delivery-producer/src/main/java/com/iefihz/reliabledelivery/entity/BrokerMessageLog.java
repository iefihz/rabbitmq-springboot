package com.iefihz.reliabledelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrokerMessageLog {

    /**
     * 消息id，使用固定规则生成，例如：prefix+时间戳+uuid
     */
    private String id;

    /**
     * 在这里存放的是Order的json串，目的：重发时，方便拿到该消息
     */
    private String message;

    /**
     * 重试次数
     */
    private Integer tryCount = 0;

    /**
     * 消息状态：0-发送中，1-发送成功，2-发送失败，使用MessageStatus表示
     */
    private String status;

    /**
     * 下一次（仅仅是第二次）重新投递消息时间，超过这个时间点，仍然没有投递成功的消息，被抓取出来，重新投递，最多重投3次，
     * 到达3次，投递仍然失败的，视为消息投递失败，这里的投递失败，是指收到ack为false或者没有收到ack的。
     */
    private Date nextRetryTime;

    /**
     * 首次投递消息时间
     */
    private Date createTime;

    /**
     * 更新消息时间
     */
    private Date updateTime;

}