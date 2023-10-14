package com.techbuzzblogs.rest.camelproject.routes;

import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsGETProcess;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class CardDetailsGETRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:TO_CardDetailsGET")
                .setHeader("CamelHttpMethod", constant("GET"))
                .to("http://localhost:9090/api/car/?bridgeEndpoint=true")
                .log("ibag rest ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsType.class))
                .log("ibag rest ${body}")
                .process(new CarDetailsGETProcess())
//                .marshal().json(JsonLibrary.Jackson) // converter o objecto em json
//                .doTry() // tratamento de exceção
//                    .doCatch().
//                .setBody(simple("Hello world, ${header.name}")) // seta o body com valores simples
//                .setBody(constant(response))
                .log(" body ${body}"); // log. ${body} retorno o body
    }
}
