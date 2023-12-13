package com.techbuzzblogs.rest.camelproject.process.telemetry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.telemetry.DynamicObjectModel;
import com.techbuzzblogs.rest.camelproject.utils.LoggerMethodUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Map;

public class SendDynamicObjectLogProcessor implements Processor {

    private static Logger LOG = LoggerFactory.getLogger(SendDynamicObjectLogProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String logEncode = exchange.getMessage().getHeader("log", String.class);
        String logDecode = decode64(logEncode);

        Map<String, String> propertiesMap = extractPropertiesObject(logDecode);

        LOG.info(new Gson().toJson(propertiesMap));

    }

    private String decode64(String value) {
        return new String(Base64.getDecoder().decode(value));
    }

    private Map<String, String> extractPropertiesObject(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DynamicObjectModel dynamicObjectModel = objectMapper.readValue(value, DynamicObjectModel.class);

        return dynamicObjectModel.getProperties();
    }
}
