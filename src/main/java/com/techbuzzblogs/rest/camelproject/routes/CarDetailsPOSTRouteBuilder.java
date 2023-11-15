package com.techbuzzblogs.rest.camelproject.routes;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypeRequest;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsPOSTRequestProcessor;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsPOSTResponseProcessor;
import com.techbuzzblogs.rest.camelproject.process.error.OperationErrorProcessor;
import com.techbuzzblogs.rest.camelproject.process.error.ErrorProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class CarDetailsPOSTRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:TO_CarDetailsPOST")
                .doTry()
                    .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                    .marshal().json(JsonLibrary.Jackson)
                    .log("BODY ${body}")
                    .unmarshal(new JacksonDataFormat(CarDetailsType.class))
                    .log("BODY object ${body}")
                    .process(new CarDetailsPOSTRequestProcessor())
                    .marshal().json(JsonLibrary.Jackson)
                    .to("http://localhost:9090/api/car/create?bridgeEndpoint=true")
//
                    .log("BODY depois Obejct ${body}")
                    .unmarshal(new GsonDataFormat(CarDetailsTypeRequest.class))
                    .process(new CarDetailsPOSTResponseProcessor())
                    .log("BODY final ${body}")
                .doCatch(Exception.class)
                    .process(new ErrorProcessor())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .doCatch(HttpOperationFailedException.class)
                    .process(new OperationErrorProcessor())
                    .marshal().json(JsonLibrary.Jackson)
                    .stop()
                .end();
    }
}


