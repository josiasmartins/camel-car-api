package com.techbuzzblogs.rest.camelproject.process;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTRequest;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CarDetailsPOSTResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        CarDetailsTypePOSTRequest body = exchange.getIn().getBody(CarDetailsTypePOSTRequest.class);

        String isYou = exchange.getIn().getHeader("isYou", String.class); // pega o header

        CarDetailsTypePOSTResponse response = CarDetailsTypePOSTResponse.builder()
                .carModel(body.getCarModel())
                .carName(body.getCarName())
                .company(body.getCompany())
                .isYou(isYou)
                .Id(body.getId()).build();

        exchange.getIn().setBody(response);
    }
}
