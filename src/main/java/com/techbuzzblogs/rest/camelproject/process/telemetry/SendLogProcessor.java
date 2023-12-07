package com.techbuzzblogs.rest.camelproject.process.telemetry;

import com.techbuzzblogs.rest.camelproject.utils.LoggUtil;
import com.techbuzzblogs.rest.camelproject.utils.LoggerMethodUtil;
import com.techbuzzblogs.rest.camelproject.utils.LoggerMethodsProcessor;
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
        LoggerMethodsProcessor.processLoggerMethods(response);
        System.out.println(response);
        Map<String, String> mapProperties = LoggerMethodUtil.extractProperties(response);

        System.out.println(mapProperties + " IBAG MAP PROPERTIES");
    }
}
