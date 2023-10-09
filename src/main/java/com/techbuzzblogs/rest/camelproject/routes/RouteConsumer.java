package com.techbuzzblogs.rest.camelproject.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class RouteConsumer extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/cole")
                .get("/helloWorld")
                .produces("text/plain")
                .outType(String.class)
                .param()
                .name("name")
                .type(RestParamType.query)
                .endParam()
                .to("direct:helloWorld");

        from("direct:helloWorld")
                .setBody(simple("Hello world, ${header.name}"))
                .log("body ${body}");
    }
    
}
