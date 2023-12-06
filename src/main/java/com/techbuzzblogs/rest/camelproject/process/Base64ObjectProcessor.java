package com.techbuzzblogs.rest.camelproject.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.lang.reflect.Field;
import java.util.Base64;

public class Base64ObjectProcessor implements Processor {

    private Boolean isEncode;

    public Base64ObjectProcessor(Boolean isEncode) {
        this.isEncode = isEncode;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();

        if (body != null) {
            processFields(body);
        }
    }

    private void processFields(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields(); // pega todos os campos da class

        for (Field field : fields) {
            field.setAccessible(true); // deixa os campos privados acessiveis

            Object value = field.get(obj); // pega o valor do campo

            if (value != null && value instanceof String) {
                // Converte o valor do campo para Base64
                String base64Value = this.encodeDecode((String) value);
                field.set(obj, base64Value);
            }
        }

    }

    private String encodeDecode(String value) {
        if (isEncode) {
           return Base64.getEncoder().encodeToString((value).getBytes());
        } else {
            return new String(Base64.getDecoder().decode((String) value));
        }
    }

}
