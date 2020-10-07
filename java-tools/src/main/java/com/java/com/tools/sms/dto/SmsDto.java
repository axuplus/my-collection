package com.java.com.tools.sms.dto;

import lombok.Data;

@Data
public class SmsDto implements java.io.Serializable {

    private String phone;
    private Integer type;
    private String code;
    private String content;
    private Long createTime;
    private Long expireTime;

    public SmsDto(String phone, Integer type, String code, String content, Long createTime, Long expireTime) {
        this.phone = phone;
        this.type = type;
        this.code = code;
        this.content = content;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }
}