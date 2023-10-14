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
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;

public class CarDetailsPOSTRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:TO_CarDetailsPOST")
                .setHeader("CamelHttpMethod", constant("POST"))
                .to("http://localhost:9090/api/car/create")
                .log("BODY ${body}")
                .unmarshal(new GsonDataFormat(CarDetailsTypePOSTRequest.class))
                .log("BODY object ${body}")
                .process(new CarDetailsPOSTRequestProcessor())
                .log("BODY depois Obejct ${body}")
                .process(new CarDetailsPOSTResponseProcessor())
                .log("BODY final ${body}");
    }
}


