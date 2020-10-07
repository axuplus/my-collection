package com.utils.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PraiseRateSerializer extends StdSerializer<String> {
    public PraiseRateSerializer() {
        this(null);
    }

    public PraiseRateSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        JsonStreamContext context = gen.getOutputContext();
        String key = getKeyName(context);
        if ("praiseRate".equals(key)) {
            if (!value.endsWith("%")) {
                String result = value + "%";
                gen.writeString(result);
                return;
            }
        }
        gen.writeString(value);

    }

    private String getKeyName(JsonStreamContext context) {
        String name = null;
        while (context != null && (name = context.getCurrentName()) == null) {
            context = context.getParent();
        }
        return name;
    }
}
