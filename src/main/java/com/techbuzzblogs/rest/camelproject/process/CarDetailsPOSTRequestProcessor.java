package com.techbuzzblogs.rest.camelproject.process;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CarDetailsPOSTRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        CarDetailsTypePOSTRequest body = exchange.getIn().getBody(CarDetailsTypePOSTRequest.class);

        exchange.getIn().setBody(body);

    }
}
