package com.techbuzzblogs.rest.camelproject.routes;

import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class RouteConsumer extends RouteBuilder {

    private String response;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .bindingMode(RestBindingMode.auto)
                .component("servlet");

        rest("/cole")
                .get("/helloWorld")
//                .produces("text/plain")
//                .outType(String.class)
                .param()
                .name("name")
                .type(RestParamType.query)
                .endParam()
                .to("direct:helloWorld");

        from("direct:helloWorld")
                .setHeader("CamelHttpMethod", constant("GET"))
                .to("http://localhost:9090/api/car/?bridgeEndpoint=true")
                .log("ibag rest ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsType.class))
                .log("ibag rest ${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        CarDetailsType body = exchange.getIn().getBody(CarDetailsType.class);

                        CarDTO dto = CarDTO.builder()
                                .carModel(body.getCarModel())
                                .carName(body.getCarName())
                                .company(body.getCompany())
                                .isNews(true).build();

                        response = new Gson().toJson(dto);
                        System.out.println(new Gson().toJson(dto) + " ibag gson");
                        exchange.getIn().setBody(dto);
                    }
                })
                .marshal().json(JsonLibrary.Jackson)
//                .doTry()
//                    .doCatch().
//                .setBody(simple("Hello world, ${header.name}"))
//                .setBody(constant(response))
                .log(response + " body ${body}");
    }
    
}
