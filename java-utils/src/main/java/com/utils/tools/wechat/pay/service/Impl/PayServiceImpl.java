package com.utils.tools.wechat.pay.service.Impl;

import com.utils.tools.wechat.pay.config.PayUtil;
import com.utils.tools.wechat.pay.config.WxPayConfig;
import com.utils.tools.wechat.pay.service.PayService;
import com.utils.util.PublicUtil;
import org.jdom2.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static com.utils.tools.wechat.pay.config.StringUtil.getIpAddr;
import static com.utils.tools.wechat.pay.config.StringUtil.getRandomString;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {


    @Override
    public Map<String, Object> weChatPay(HttpServletRequest request) {
        try {
            //生成的随机字符串
            String nonce_str = getRandomString(32);
            //商品名称
            String body = "运费";
            //获取客户端的ip地址
            String spbill_create_ip = getIpAddr(request);
            // TODO: 2020/4/23  appID
            String openid = "";
            System.out.println("openid--->>>" + openid);
            // TODO: 2020/4/23 雪花ID即刻
            String out_trade_no = String.valueOf(123456789);
            // TODO: 2020/4/23 一百块
            String total_fee = String.valueOf(new BigDecimal(100).multiply(new BigDecimal(100)).intValue());
            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", "");
            packageParams.put("mch_id", WxPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", out_trade_no);//商户订单号
            packageParams.put("total_fee", total_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WxPayConfig.notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", WxPayConfig.TRADETYPE);//支付方式
            packageParams.put("openid", openid);
            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();
            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            // TODO: 2020/4/23  appID
            String xml = "<xml>" + "<appid>" + "appID" + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WxPayConfig.notify_url + "</notify_url>"
                    + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + total_fee + "</total_fee>"
                    + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";
            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);
            String return_code = (String) map.get("return_code");//返回状态码
            Map<String, Object> response = new HashMap<>();//返回给小程序端需要的参数
            if (return_code.equals("SUCCESS")) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                // TODO: 2020/4/23  appID
                String stringSignTemp = "appId=" + "appID" + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=" + WxPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                response.put("paySign", paySign);
                response.put("signType", WxPayConfig.SIGNTYPE);
                /**
                 * 做自己的交易记录业务代码
                 */
            }
            // TODO: 2020/4/23  appID
            response.put("appid", "appID");
            response.put("state", 1);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void notify(HttpServletResponse response, HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml
            String notityXml = sb.toString();
            String resXml = "";
            System.out.println("接收到的报文：" + notityXml);
            Map map = PayUtil.doXMLParse(notityXml);
            System.out.println("Notify Map--->>>" + map.toString());
            String returnCode = (String) map.get("return_code");
            String out_trade_no = (String) map.get("out_trade_no");
            if ("SUCCESS".equals(returnCode)) {
                //验证签名是否正确
                Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
                String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
                String sign = PayUtil.sign(validStr, WxPayConfig.key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
                //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
                if (sign.equals(map.get("sign"))) {
                    /**
                     *  业务代码  可以先去查出交易表
                     */

                    if (PublicUtil.isNotEmpty("交易记录对象")) {

                        /**
                         *  业务代码 可以给用户钱包增加金额 ps不是微信钱包
                         */

                        //通知微信服务器已经支付成功
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    }
                    /**此处添加自己的业务逻辑代码end**/
                }
            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            System.out.println(resXml);
            System.out.println("微信支付回调数据结束");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
