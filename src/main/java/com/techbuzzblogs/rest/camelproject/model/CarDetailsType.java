package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techbuzzblogs.rest.camelproject.decorators.JsonConverterField;
import com.techbuzzblogs.rest.camelproject.decorators.Logger;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Builder
public class CarDetailsType {

//    @JsonProperty("carNames")
//    @JsonAlias("carName")
    @JsonConverterField("carName")
    @Logger
    private String carName;

    @Logger
    private String carModel;
    private String company;

}
