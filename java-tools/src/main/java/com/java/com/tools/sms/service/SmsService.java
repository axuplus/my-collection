package com.java.com.tools.sms.service;


import com.java.com.tools.sms.vo.SmsSendVo;

public interface SmsService {

    SmsSendVo send(String phone, Integer type);

    boolean verifyCode(String phone, String code);

}
