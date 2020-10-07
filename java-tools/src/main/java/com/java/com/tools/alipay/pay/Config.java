package com.java.com.tools.alipay.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class Config {

    /**
     * 应用公钥证书路径
     */
    public String APP_CERT_PATH;

    /**
     * 支付宝公钥证书文件路径
     */
    public String ALIPAY_CERT_PATH;

    /**
     * 支付宝CA根证书文件路径
     */
    public String ALIPAY_ROOT_CERT_PATH;



}
