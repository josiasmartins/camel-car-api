package com.techbuzzblogs.rest.camelproject.process;

import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CarDetailsPOSTRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        CarDetailsType body = exchange.getIn().getBody(CarDetailsType.class);

        exchange.getIn().setBody(body);

    }
}
