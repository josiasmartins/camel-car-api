package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarDTO {
    @JsonProperty("carName")
    private String carName;
    private String carModel;
    private String company;
    private Boolean isNews;
}
