package com.utils.tools.sms.service;


import com.utils.tools.sms.vo.SmsSendVo;

public interface SmsService {

    SmsSendVo send(String phone, Integer type);

    boolean verifyCode(String phone, String code);

}
