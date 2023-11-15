package com.techbuzzblogs.rest.camelproject.process.error;

import com.techbuzzblogs.rest.camelproject.model.ErrorTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OperationErrorProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(OperationErrorProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("[OperationErrorProcessor] Iniciando OperationErrorProcessor");
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        logger.error("[OperationErrorProcessor] {} ", e.getMessage());


        UUID traceID = exchange.getMessage().getHeader("X-traceId", UUID.class);
        exchange.getMessage().setBody(ErrorTemplate.builder() // faz um tratamento padronizado de exxceção
                ._errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                ._message("id não encontrado")
                ._details("id não existe no banco de dados")
                ._timestamp("")
                ._traceId(traceID)
                .build());

        exchange.getMessage().removeHeader("*");
        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());

        logger.info("[OperationErrorProcessor] finalizando ErrorProcessor");

    }
}
