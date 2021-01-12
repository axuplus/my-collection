package com.utils.tools.alipay.pay.real;

import com.alibaba.fastjson.JSONObject;
import com.utils.tools.alipay.pay.AliUtils;
import com.utils.util.wrapper.WrapMapper;
import com.utils.util.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
public class PayServiceImpl implements PayService {



    @Autowired
    private AliUtils aliUtils;

    @Override
    public Wrapper aliAttestation(String mapSource) {
        JSONObject jsonObject = JSONObject.parseObject(mapSource);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get("map").toString());
        JSONObject jsonObject2 = JSONObject.parseObject(jsonObject1.get("result").toString());
        Map<String, Object> map2 = jsonObject2.getInnerMap();
        Map<String, String> map = new LinkedHashMap<>();
        for (String s : map2.keySet()) {
            if (s.equals("sign_type")) {
                continue;
            }
            map.put(s, map2.get(s).toString());
        }
        String content = jsonObject1.get("result").toString();
        content = content.substring(content.indexOf(":{") + 1, content.lastIndexOf("sign\":") - 2);
        boolean result = aliUtils.aliAttestation(map, content);
        if (result) {
            switch (jsonObject1.getString("resultStatus")) {
                case "9000":
                case "6004":
                case "8000":
                    return WrapMapper.ok("支付成功");
                case "4000":
                    return WrapMapper.error("订单支付失败");
                case "5000":
                    return WrapMapper.error("重复请求");
                case "6001":
                    return WrapMapper.error("用户中途取消");
                case "6002": {
                    return WrapMapper.error("网络连接出错");
                }
                default:
                    return WrapMapper.error("其它支付错误");
            }
        }
        return WrapMapper.error("支付宝验签失败！");
    }

    @Override
    public String aliPayReturn(Map<String, String> map) {
        boolean result = aliUtils.aliReturnAttestation(map);
        if (!result) {
            return "failure";
        }
        String orderNumber = map.get("out_trade_no");
        //用户支付金额
        String payMoney = map.get("total_amount");
        Long id = null;
        /**
         *  业务
         */
//        try {
//            id = (Long) redisUtil.hget(RedisKey.ALI_ORDER_NUMBER, orderNumber);
//        } catch (Exception e) {
//            return "failure";
//        }
//        if (!map.get("trade_status").equals("TRADE_SUCCESS")) {
//            return "success";
//        }
//        LoanRepaymentItem item = loanRepaymentItemMapper.selectByPrimaryKey(id);
//        if (!item.getRepaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString().equals(payMoney)) {
//            return "failure";
//        }
//        if (!map.get("app_id").equals(AliUtils.APP_ID)) {
//            return "failure";
//        }
//        return successOpration(orderNumber, item, map, (byte) 2);
        return null;
    }
}
