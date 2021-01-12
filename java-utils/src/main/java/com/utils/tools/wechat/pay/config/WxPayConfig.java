package com.utils.tools.wechat.pay.config;

public class WxPayConfig {
    //公众号appid
    public static final String gzhAppId = "123456";
    //小程序appid
    public static final String xcxAppId = "123456";
    //微信支付的商户id
    public static final String mch_id = "123456";
    //微信支付的商户密钥
    public static final String key = "123456";
    //支付成功后的服务器回调url
    public static final String notify_url = "url";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
