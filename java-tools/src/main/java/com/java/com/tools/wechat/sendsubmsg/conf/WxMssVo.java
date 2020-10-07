package com.java.com.tools.wechat.sendsubmsg.conf;

import lombok.Data;

/**
 * @description 模板消息请求参数封装 data为Object可以传输任何模板data
 * @author: Joma
 */
@Data
public class WxMssVo {
    private String access_token;
    private String touser;
    private String request_url;
    private String template_id;
    private String page;
//    private String form_id; 已经废除 之前的模板消息需要这个 现在是订阅 所以不需要from_id
    private Object data;
    private int messageType = 1;//1：小程序模板消息  2：公众号模板消息
}