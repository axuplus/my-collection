package com.utils.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class IdSerializer extends StdSerializer<Long> {
    public IdSerializer() {
        this(null);
    }

    public IdSerializer(Class<Long> t) {
        super(t);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        JsonStreamContext context = gen.getOutputContext();
        String key = getKeyName(context);
        if ("id".equals(key) || StringUtils.endsWithAny(key, "Id", "Ids", "ids", "id")) {
            gen.writeString(value.toString());
            return;
        }

        gen.writeNumber(value);
    }

    private String getKeyName(JsonStreamContext context) {
        String name = null;
        while (context != null && (name = context.getCurrentName()) == null) {
            context = context.getParent();
        }
        return name;
    }
}