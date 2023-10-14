package com.techbuzzblogs.rest.camelproject.process;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypeRequest;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypeResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CarDetailsPOSTResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        CarDetailsTypeRequest body = exchange.getIn().getBody(CarDetailsTypeRequest.class);

        String isYou = exchange.getIn().getHeader("isYou", String.class); // pega o header

        CarDetailsTypeResponse response = CarDetailsTypeResponse.builder()
                .carModel(body.getCarModel())
                .carName(body.getCarName())
                .company(body.getCompany())
                .isYou(isYou)
                .id(body.getId()).build();

        exchange.getIn().setBody(response);
    }
}
