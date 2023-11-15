package com.techbuzzblogs.rest.camelproject.routes;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsGETProcessor;
import com.techbuzzblogs.rest.camelproject.process.error.ErrorProcessor;
import com.techbuzzblogs.rest.camelproject.process.error.OperationErrorProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class CardDetailsGETRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:TO_CarDetailsGET")
                .doTry()
                    .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
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
                    .log(" body ${body}") // log. ${body} retorno o body
//                    .doCatch(Exception.class)
//                        .process(new ErrorProcessor())
//                        .marshal().json(JsonLibrary.Jackson)
//                        .stop()
                    .doCatch(HttpOperationFailedException.class)
                        .process(new OperationErrorProcessor())
                        .marshal().json(JsonLibrary.Jackson)
                        .log("${body}")
                        .stop()
                    .end();
    }
}
