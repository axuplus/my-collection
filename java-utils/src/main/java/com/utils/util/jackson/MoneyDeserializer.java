package com.utils.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class MoneyDeserializer extends StdDeserializer<Long> {
    public MoneyDeserializer() {
        this(null);
    }

    public MoneyDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * 金额反序列化
     * 小数 -> Long
     * 保留四位小数，放大10000倍
     */
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = StringUtils.trim(p.getText());

        if (StringUtils.isEmpty(text) || "null".equals(text)) {
            return null;
        }

        double v = Double.parseDouble(text);
        return (long) (v * 10000);
    }
}