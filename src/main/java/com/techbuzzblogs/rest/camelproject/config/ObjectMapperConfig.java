package com.techbuzzblogs.rest.camelproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;


@Configuration
public class ObjectMapperConfig {

    private static final ObjectMapper INSTANCE = new ObjectMapper();

    public ObjectMapperConfig() {}

    @Bean
    public ObjectMapper getInstance() {

        if (INSTANCE.getRegisteredModuleIds() == null ||
                (INSTANCE.getRegisteredModuleIds() != null &&
                        !INSTANCE.getRegisteredModuleIds().contains("com.fasterxml.jackson.jsr310.JavaTimeModule"))
        ) {
            INSTANCE.registerModule(new JavaTimeModule().addSerializer(OffsetDateTimeSerializer.INSTANCE));
        }

        if (INSTANCE.getPropertyNamingStrategy() == null) {
            INSTANCE.setPropertyNamingStrategy(new PropertyNamingStrategyLowerCaseWithUnderscoresStrategy());
        }

        return INSTANCE;

    }

}
