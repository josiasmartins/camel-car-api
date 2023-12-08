package com.techbuzzblogs.rest.camelproject.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.utils.Console;
import com.techbuzzblogs.rest.camelproject.utils.DynamicObjectUtil;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Base64;

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

//        String jsonString = "{\"prop1\": \"value1\", \"prop2\": 42, \"prop3\": true}";

//        String log = exchange.getMessage().getHeader("log").toString();
        String log = logParam(exchange);

        ObjectMapper objectMapper = new ObjectMapper();
        DynamicObjectUtil dynamicObject = objectMapper.readValue(log, DynamicObjectUtil.class);



        System.out.println(dynamicObject);

        System.out.println("CONVERTIDO EM JSON: " +json);
        System.out.println("CONVERTIDO EM OBJECTO JAVA: " + objectJava);

        exchange.getIn().setBody(dto);

        Console.ln("OBJECTO SETADO NO EXCHANGE");

       String java =  "{name: naruto, status: callvante}";
    }

    private String decodeBase64(String value) {
            return new String(Base64.getDecoder().decode((String) value));
    }

    private String logParam(Exchange exchange) {
        return decodeBase64(exchange.getMessage().getHeader("log").toString());
//        return new Gson().toJson(log);
    }
}
