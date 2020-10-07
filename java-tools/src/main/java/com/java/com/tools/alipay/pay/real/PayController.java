package com.java.com.tools.alipay.pay.real;

import com.utils.util.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class PayController {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private PayService payService;

    @ApiOperation("支付宝验签")
    @PostMapping("/aliAttestation")
    public Wrapper aliAttestation(@RequestBody String map) {
        return payService.aliAttestation(map);
    }


    @PostMapping("/aliPayReturn")
    public String aliPayReturn(HttpServletRequest request) {
        Map<String, String> param = convertRequestParamsToMap(request); // 将异步通知中收到的待验证所有参数都存放到map中
        logger.info("支付宝回调信息------>>>>>>>>" + param.toString());
        return payService.aliPayReturn(param);
    }



    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        log.info("requestParams---->>>> " + requestParams);
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
