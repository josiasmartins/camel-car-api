package com.techbuzzblogs.rest.camelproject.routes;

import com.google.gson.Gson;
import com.techbuzzblogs.rest.camelproject.model.CarDTO;
import com.techbuzzblogs.rest.camelproject.model.CarDetailsType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
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


        rest("/camel/api") // contexto do path
                .get("/car") // chamada GET
//                .produces("text/plain")
//                .outType(String.class)
                .param() // adiciona paramatros da requisicao
                    .name("name")
                    .type(RestParamType.query) // tipo de parametro
                    .endParam() // finaliza o parametro
                .to("direct:TO_CardDetailsGET"); // chama o componente TO_CardDetailsGET
    }
    
}
