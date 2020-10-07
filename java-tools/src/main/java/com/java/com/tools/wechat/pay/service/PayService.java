package com.java.com.tools.wechat.pay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface PayService {


    Map<String, Object> weChatPay(HttpServletRequest request);

    // TODO: 2020/4/23 回调的controller自己写 url必须要 因为要回调
    void notify(HttpServletResponse response, HttpServletRequest request);


}
