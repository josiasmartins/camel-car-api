package com.techbuzzblogs.rest.camelproject.routes;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsGETProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class CardDetailsGETRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:TO_CarDetailsGET")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("http://localhost:9090/api/car/${header.id}?bridgeEndpoint=true")
                .log("ibag rest ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsType.class))
                .log("ibag rest ${body}")
                .process(new CarDetailsGETProcessor())
//                .marshal().json(JsonLibrary.Jackson) // converter o objecto em json
//                .doTry() // tratamento de exceção
//                    .doCatch().
//                .setBody(simple("Hello world, ${header.name}")) // seta o body com valores simples
//                .setBody(constant(response))
                .log(" body ${body}"); // log. ${body} retorno o body
    }
}
