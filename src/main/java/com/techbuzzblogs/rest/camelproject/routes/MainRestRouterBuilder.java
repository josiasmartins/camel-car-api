package com.techbuzzblogs.rest.camelproject.routes;

import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTRequest;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsTypePOSTResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MainRestRouterBuilder extends RouteBuilder {

    private String response;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .bindingMode(RestBindingMode.json) // transforma a resposta em json no final
                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_EMPTY_BEANS")
                .component("servlet"); // seta o componente servlet que possui um servidor integradotomcat


        rest("/camel/api/car/{id}") // contexto do path
                .get() // chamada GET
//                .produces("text/plain")
//                .outType(String.class)
                .param() // adiciona paramatros da requisicao
                    .name("id")
                    .type(RestParamType.path) // param da rota tets/ess/{id}
                    .endParam() // finaliza o parametro
                .to("direct:TO_CarDetailsGET"); // chama o componente TO_CardDetailsGET

        rest("/camel/api/car/create")
                .post()
//                .type(CarDetailsType.class)
                .param()
                    .name("isYou")
                    .type(RestParamType.query)
                    .endParam()
//                .outType(CarDetailsTypePOSTResponse.class)
//                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:TO_CarDetailsPOST");
    }
    
}
