package com.techbuzzblogs.rest.camelproject.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

public class PropertyNamingStrategyLowerCaseWithUnderscoresStrategy extends PropertyNamingStrategyBase {

    private static final long serialVersionUUID = -645730779221830768L;

    @Override
    public String translate(String propertyName) {
        return "_" + propertyName;
    }

}
