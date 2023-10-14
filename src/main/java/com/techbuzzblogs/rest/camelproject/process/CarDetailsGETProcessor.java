package com.techbuzzblogs.rest.camelproject.process;

import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.utils.Console;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CarDetailsGETProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        CarDetailsType body = exchange.getIn().getBody(CarDetailsType.class);

        CarDTO dto = CarDTO.builder()
                .carModel(body.getCarModel())
                .carName(body.getCarName())
                .company(body.getCompany())
                .isNews(true).build();

        String json = new Gson().toJson(dto); // transforma em json
        CarDTO objectJava = new Gson().fromJson(json, CarDTO.class); // transforma o json em objeto java

        System.out.println("CONVERTIDO EM JSON: " +json);
        System.out.println("CONVERTIDO EM OBJECTO JAVA: " + objectJava);

        exchange.getIn().setBody(dto);

        Console.ln("OBJECTO SETADO NO EXCHANGE");
    }
}
