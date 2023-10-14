package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techbuzzblogs.rest.camelproject.decorators.JsonConverterField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDetailsType {

//    @JsonProperty("carNames")
//    @JsonAlias("carName")
    @JsonConverterField("carName")
    private String carName;
    private String carModel;
    private String company;

}
