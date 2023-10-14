package com.techbuzzblogs.rest.camelproject.routes;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTRequest;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTResponse;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsPOSTRequestProcessor;
import com.techbuzzblogs.rest.camelproject.process.CarDetailsPOSTResponseProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.springframework.stereotype.Component;

@Component
public class CarDetailsPOSTRouteBuilder extends RouteBuilder {


//    Exchange.HTTP_METHOD
//    HttpMethod.POST

    @Override
    public void configure() throws Exception {
        from("direct:TO_CarDetailsPOST")
                .setHeader("CamelHttpMethod", constant("POST"))
                .marshal().json(JsonLibrary.Jackson)
                .log("BODY ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsType.class))
                .log("BODY object ${body}")
                .process(new CarDetailsPOSTRequestProcessor())
                .marshal().json(JsonLibrary.Jackson)
                .to("http://localhost:9090/api/car/create?bridgeEndpoint=true")

                .log("BODY depois Obejct ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsTypePOSTResponse.class))
                .process(new CarDetailsPOSTResponseProcessor())
                .log("BODY final ${body}");
    }
}


