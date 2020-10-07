package com.java.com.tools.wechat.sendsubmsg.service.Impl;
import com.java.com.tools.wechat.sendsubmsg.conf.CommonUtil;
import com.java.com.tools.wechat.sendsubmsg.conf.TemplateIdConfig;
import com.java.com.tools.wechat.sendsubmsg.conf.Token;
import com.java.com.tools.wechat.sendsubmsg.conf.WxMssVo;
import com.java.com.tools.wechat.sendsubmsg.data.StateTemplateData;
import com.java.com.tools.wechat.sendsubmsg.service.WeChatSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  1：鉴于微信token的一次性特殊性 所以这里的消息推送分开了 就是一个模板一个service 要不然很容易出现推送不成功
 *  2：现在是订阅消息 每个模板每次订阅只能推送一次 要不然会报43101用户拒绝接受消息
 *  3：WxMssVo里面的字段不能改变 要不然会报47003参数不正确
 *  4：参考官网链接 https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
 */

@Slf4j
@Service
public class WeChatSmsServiceImpl implements WeChatSmsService {

    @Override
    public String sendStateSms() {
        String phone = "17508959593";
        String openId = "用户的微信ID";
        Token token = CommonUtil.getToken("你的Appid", "你的Secret");
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setAccess_token(token.getAccessToken());
        wxMssVo.setTouser(openId);
        wxMssVo.setTemplate_id(TemplateIdConfig.StateTemplateId);
        wxMssVo.setPage("pages/index/index"); // TODO: 2020/4/23 点击这条消息跳转到首页
        wxMssVo.setRequest_url("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + token.getAccessToken());
        /**
         *  这里写业务代码 然后带进去参数里面
         */
        StateTemplateData templateData = new StateTemplateData();
        templateData.setCharacter_string1(new StateTemplateData.CharacterString1("长度看官网限制"));
        templateData.setPhrase2(new StateTemplateData.Phrase2("长度看官网限制"));
        templateData.setName3(new StateTemplateData.Name3("长度看官网限制"));
        templateData.setPhone_number4(new StateTemplateData.Phone4(Long.valueOf("长度看官网限制")));
        templateData.setThing6(new StateTemplateData.Thing6("长度看官网限制"));
        wxMssVo.setData(templateData);
        CommonUtil.sendTemplateMessage(wxMssVo);
        return ("成功推送至 " + phone);
    }
}
