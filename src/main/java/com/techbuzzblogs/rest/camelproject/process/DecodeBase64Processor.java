package com.techbuzzblogs.rest.camelproject.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.lang.reflect.Field;
import java.util.Base64;

public class DecodeBase64Processor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();

        if (body != null) {
            decodeFields(body);
        }
    }

    private void decodeFields(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object value = field.get(obj);

            if (value != null && value instanceof String) {
                // Decodifica o valor do campo de Base64
                String decodedValue = new String(Base64.getDecoder().decode((String) value));
                field.set(obj, decodedValue);
            }
        }
    }
}
