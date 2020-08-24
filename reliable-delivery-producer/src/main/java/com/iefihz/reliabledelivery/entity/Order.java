package com.iefihz.reliabledelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 9111357402963030257L;

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单描述
     */
    private String desc;

    /**
     * 消息id，使用固定规则生成，例如：prefix+时间戳+uuid
     */
    private String messageId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
