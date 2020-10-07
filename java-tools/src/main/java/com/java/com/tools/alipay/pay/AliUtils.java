package com.java.com.tools.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class AliUtils {
    //prod
    public static final String APP_ID = "";

    private static final String GATEWAY = "https://openapi.alipay.com/gateway.do";

    private static final String PRIVATEKEY = "";

    private static final String ALIPAY_PUBLIC_KEY = "";

    //dev
//    private static final String GATEWAY = "https://openapi.alipaydev.com/gateway.do";
//
//    public static final String APP_ID = "";
//
//    private static final String PRIVATEKEY = "沙箱公钥";
//
//    private static final String ALIPAY_PUBLIC_KEY = "沙箱私钥";

    private static final String CHARSET = "utf-8";

    private static final String SIGN_TYPE = "RSA2";

    private static final String NOTIFY_URL = "回调业务url";

    /**
     * 目前支付宝支付有两种：一种是公钥,种是证书,后者支持付款以及转账到支付宝账户,
     * 有转账业务必须要用证书的方式,目前支付宝不支持转账到银行卡
     */

//    private static final String CERT_PATH = "C:/Users/Administrator/Desktop/CSR/alipay/appCertPublicKey_2021001157613961.crt";
//
//    private static final String ALIPAY_PUBLIC_CERT_PATH = "C:/Users/Administrator/Desktop/CSR/alipay/alipayCertPublicKey_RSA2.crt";
//
//    private static final String ROOT_CERT_PATH = "C:/Users/Administrator/Desktop/CSR/alipay/alipayRootCert.crt";

    @Resource
    private Config config;

    /**
     * 支付调用
     *
     * @param price    支付金额
     * @param orderNum 订单号
     * @return 支付宝返回
     */
    public AlipayTradeAppPayResponse pay(BigDecimal price, String orderNum) {
        final CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(GATEWAY);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(PRIVATEKEY);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        // 打完包以后去读线上上传的证书
        certAlipayRequest.setCertPath(config.APP_CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(config.ALIPAY_CERT_PATH);
        certAlipayRequest.setRootCertPath(config.ALIPAY_ROOT_CERT_PATH);
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setTimeoutExpress("20m");
        model.setTotalAmount(price.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        model.setSubject("还款");
        model.setOutTradeNo(orderNum);
        request.setBizModel(model);
        request.setApiVersion("2.0");
        request.setNotifyUrl(NOTIFY_URL);
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            System.err.println(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;
    }


    /**
     * 支付宝对公账户转账到用户账户  注意：对象商户要去签约 90天入驻时间
     * @param price
     * @param orderNumber
     * @return AlipayFundTransUniTransferResponse
     */
    public AlipayFundTransUniTransferResponse payToAccount(BigDecimal price, String orderNumber, String account, String name) {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(GATEWAY);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(PRIVATEKEY);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setCertPath(config.APP_CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(config.ALIPAY_CERT_PATH);
        certAlipayRequest.setRootCertPath(config.ALIPAY_ROOT_CERT_PATH);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        /**
         * {
         *     "out_biz_no":"201806300001",
         *     "trans_amount":"23.00",
         *     "product_code":"TRANS_ACCOUNT_NO_PWD",
         *     "biz_scene":"DIRECT_TRANSFER",
         *     "order_title":"转账标题",
         *     "original_order_id":"20190620110075000006640000063056",
         *     "payee_info":{
         *         "identity":"208812*****4123",
         *         "identity_type":"ALIPAY_USER_ID",
         *         "name":"黄龙国际有限公司"
         *     },
         *     "remark":"单笔转账",
         *     "business_params":{
         *         "sub_biz_scene":"REDPACKET"
         *     }
         * }
         */
        String amount = price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + orderNumber + "\"," +
                "\"trans_amount\":\"" + amount + "\"," +
                "\"product_code\":\"" + "TRANS_ACCOUNT_NO_PWD" + "\"," +
                "\"biz_scene\":\"" + "DIRECT_TRANSFER" + "\"," +
                "\"order_title\":\"" + "测试还款标题" + "\"," +
                "\"payee_info\":{" +
                "\"identity\":\"" + account + "\"," +
                "\"identity_type\":\"" + "ALIPAY_LOGON_ID" + "\"," +
                "\"name\":\"" + name + "\"" +
                "}," +
                "\"remark\":\"" + "单笔转账" + "\"" +
                "}");
        AlipayFundTransUniTransferResponse response = null;
        try {
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;
    }

    /**
     *  证书验签即可用付款也可用转账 就一种
     */

    /**
     * 验签
     * @param params
     * @return
     */
    public boolean aliAttestation(Map<String, String> params, String content) {
        try {
            return AlipaySignature.rsaCertCheck(content, params.get("sign"), config.ALIPAY_CERT_PATH, CHARSET, SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 阿里回调验签
     * @param params
     * @return
     */
    public boolean aliReturnAttestation(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCertCheckV1(params, config.ALIPAY_CERT_PATH, CHARSET, SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 转账验签
     */
    public boolean checkSign() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(GATEWAY);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(PRIVATEKEY);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setCertPath(config.APP_CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(config.ALIPAY_CERT_PATH);
        certAlipayRequest.setRootCertPath(config.ALIPAY_ROOT_CERT_PATH);
        try {
            DefaultAlipayClient response = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 这里还差对response的解析
        return false;
    }


}
