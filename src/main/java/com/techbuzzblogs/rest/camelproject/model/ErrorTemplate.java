package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorTemplate {

    @JsonProperty("_errorCode")
    private Integer _errorCode;

    @JsonProperty("_message")
    private String _message;

    @JsonProperty("_details")
    private String _details;

    @JsonProperty("timestamp")
    private OffsetDateTime _timestamp;

    @JsonProperty("_traceId")
    private UUID _traceId;

    @JsonProperty("_errors")
    @Valid
    private List<ErrorTemplateErrors>  _errors;

}
