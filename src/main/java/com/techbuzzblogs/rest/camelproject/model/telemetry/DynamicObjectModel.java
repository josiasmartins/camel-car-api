package com.techbuzzblogs.rest.camelproject.model.telemetry;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class DynamicObjectModel {

    private Map<String, String> properties = new HashMap<>();

    @JsonAnySetter
    public Map<String, String> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void setProperties(String key, String value) {
        properties.put(key, value);
    }

}
