package com.java.com.tools.wechat.sendsubmsg.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  这是其中一个模板 这些参数字段一定要对应微信那边申请的字段 要不然会发送失败
 */
@Data
public class StateTemplateData {
    private CharacterString1 character_string1;
    private Phrase2 phrase2;
    private Name3 name3;
    private Phone4 phone_number4;
    private Thing6 thing6;
    @Data
    @AllArgsConstructor
    public static class CharacterString1{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Phrase2{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Name3{
        private String value;
    }
    @Data
    @AllArgsConstructor
    public static class Phone4{
        private Long value;
    }
    @Data
    @AllArgsConstructor
    public static class Thing6{
        private String value;
    }

}
