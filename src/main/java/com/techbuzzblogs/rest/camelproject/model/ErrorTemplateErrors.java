package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorTemplateErrors {

    @JsonProperty("_code")
    private Integer _code;

    @JsonProperty("_field")
    private String _field;

    @JsonProperty("_message")
    private String _messages;

}
