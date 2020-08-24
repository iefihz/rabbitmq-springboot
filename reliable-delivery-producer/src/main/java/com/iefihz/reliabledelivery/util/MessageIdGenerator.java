package com.iefihz.reliabledelivery.util;

import java.util.UUID;

/**
 * 消息id生成器
 */
public class MessageIdGenerator {

    public static String generateMessageId(String prefix) {
        return prefix + "_" + System.currentTimeMillis() + "_" + generateUUID();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
