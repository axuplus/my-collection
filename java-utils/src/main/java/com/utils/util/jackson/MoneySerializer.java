package com.utils.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;

public class MoneySerializer extends StdSerializer<Long> {
    public MoneySerializer() {
        this(null);
    }

    public MoneySerializer(Class<Long> t) {
        super(t);
    }

    /**
     * 金额序列化
     * Long -> 小数
     * 缩小10000倍
     */
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        BigDecimal result = new BigDecimal(value.toString()).divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_EVEN);
        gen.writeNumber(result.toPlainString());
    }
}