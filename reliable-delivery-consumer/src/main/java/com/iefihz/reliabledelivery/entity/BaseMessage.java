package com.iefihz.reliabledelivery.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class BaseMessage implements Serializable {

    private static final long serialVersionUID = 9111357402963030257L;

    /**
     * 消息id，使用固定规则生成，例如：prefix+时间戳+uuid
     */
    @Getter
    @Setter
    private String messageId;

}
