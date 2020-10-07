package com.java.com.tools.sms.service.impl;
import com.java.com.tools.sms.dto.SmsDto;
import com.java.com.tools.sms.service.SmsService;
import com.java.com.tools.sms.vo.SmsSendVo;
import com.utils.util.NumberUtils;
import com.utils.util.PublicUtil;
import com.utils.util.constant.GlobalConstant;
import com.utils.util.date.DateUtil;
import com.utils.util.encrypt.Md5Utils;
import com.utils.util.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@Service
public class SmsServiceImpl implements SmsService {

    private final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    private static final String USERNAME = "cgh00001";
    private static final String PASSWORD = "cgh00001";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SmsSendVo send(String phone, Integer type) {
        SmsSendVo smsSendVo = new SmsSendVo();
        smsSendVo.setPhone(phone);
        Date date = new Date();
        String password = Md5Utils.md5Str(PASSWORD + DateUtil.getStringYYYYMMDDHHMMSS(date));
        SmsDto sms = getSms(phone, type);
        if (PublicUtil.isNotEmpty(sms)) {
            String uri = null;
            try {
                uri = "http://api.sms1086.com/Api/Send.aspx?username=" + USERNAME + "&password=" + password + "&mobiles=" + phone + "&content=" + URLEncoder.encode(sms.getContent(), "GB2312").toString() + "&f=1&timestamp=" + DateUtil.getStringYYYYMMDDHHMMSS(date);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            uri = uri.replaceAll(" ", "%20");
            logger.info("uri--->>>" + uri);
            String sendResult = HttpUtils.DO_GET(uri, null, null);
            logger.info("sendResult--->>>" + sendResult);
            if (sendResult.contains("result=0")) {
                smsSendVo.setState(1);
                redisTemplate.opsForHash().put(GlobalConstant.SMS, phone, sms);
            }
        }
        return smsSendVo;
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        Date date = new Date();
        SmsDto smsDto = (SmsDto) redisTemplate.opsForHash().get(GlobalConstant.SMS, phone);
        if (PublicUtil.isNotEmpty(smsDto)) {
            if (smsDto.getCode().equals(code)) {
                if (DateUtil.currentTime(date) <= smsDto.getExpireTime()) {
                    return true;
                }
            }
        }
        return false;
    }

    private SmsDto getSms(String phone, Integer type) {
        Date date = new Date();
        SmsDto smsDto = null;
        switch (type) {
            case 1:
                String vc = NumberUtils.getRandomNumber(6);
                smsDto = new SmsDto(phone, type, vc, "您的验证码为：" + vc + "，请在15分钟之内完成验证【某某科技公司】", DateUtil.currentTime(date), DateUtil.currentTime(DateUtil.addMinute(date, 15)));
                break;
        }
        return smsDto;
    }
}
