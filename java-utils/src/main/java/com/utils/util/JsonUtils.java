package com.utils.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.List;

public class JsonUtils {

    public static Object toJSON(Object o) {
        return JSON.toJSON(o);
    }

    public static Object toJSON(Object o, ParserConfig config) {
        return JSON.toJSON(o, config);
    }

    public static Object toJSON(Object o, SerializeConfig config) {
        return JSON.toJSON(o, config);
    }

    /**
     * @throws
     * @Title:
     * @Description: Bean转JSON
     * @param: @param o
     * @param: @return
     * @date: 2017-9-30 下午1:10:23
     */
    public static String toJSONString(Object o) {
        return JSON.toJSONString(o);
    }

    /**
     * @throws
     * @Title:
     * @Description: Bean转JSON
     * @param: @param o
     * @param: @param prettyFormat 是否格式化
     * @param: @return
     */
    public static String toJSONString(Object o, Boolean prettyFormat) {
        return JSON.toJSONString(o, prettyFormat);
    }


    /**
     * @throws
     * @Title:
     * @Description: JSON转Bean
     * @param: @param json
     * @param: @param clazz
     * @param: @return
     */
    public static <T> T fromJSONToBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * @throws
     * @Title: JSON转BeanList
     * @Description:
     * @param: @param json
     * @param: @param clazz
     */
    public static <T> List<T> fromJSONToListBean(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }



}
