package com.techbuzzblogs.rest.camelproject.process.telemetry;

import com.techbuzzblogs.rest.camelproject.utils.LoggUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

public class SendLogProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Object response = exchange.getIn().getBody(Object.class);
        // retorna o endpoint
        String httpUri = exchange.getIn().getHeader("CamelHttpUri").toString();

        // retorna todas as propriedades mapeadas com @Logger
        Map<String, String> mapProperties = LoggUtil.extractProperties(response);

        System.out.println(mapProperties + " IBAG MAP PROPERTIES");
    }
}
