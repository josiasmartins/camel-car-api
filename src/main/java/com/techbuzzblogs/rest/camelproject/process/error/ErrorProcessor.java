package com.techbuzzblogs.rest.camelproject.process.error;

import com.techbuzzblogs.rest.camelproject.model.ErrorTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ErrorProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ErrorProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("[ErrorProcessor] Iniciando ErrorProcessor");
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        logger.error("[ErrorProcessor] {} ", e.getMessage());

        logger.info("[ErrorProcessor] {} ", e.getMessage());

        UUID traceID = exchange.getMessage().getHeader("X-traceId", UUID.class);
        exchange.getMessage().setBody(ErrorTemplate.builder() // faz um tratamento padronizado de exxceção
                ._errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                ._message("Internal Server Error")
                ._details("An internal server error ocurred while performing this action. Contact the Administractor")
                ._timestamp(OffsetDateTime.now())
                ._traceId(traceID)
                .build());

    }
}
