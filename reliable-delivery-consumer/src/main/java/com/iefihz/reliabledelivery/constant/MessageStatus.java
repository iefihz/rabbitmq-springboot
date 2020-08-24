package com.iefihz.reliabledelivery.constant;

/**
 * 消息状态，指消息是否成功发送到消息队列中
 */
public interface MessageStatus {

    /**
     * 发送中
     */
    public static final String SENDING = "0";

    /**
     * 发送成功
     */
    public static final String SEND_SUCCESS = "1";

    /**
     * 发送失败
     */
    public static final String SEND_FAILURE = "2";

}
