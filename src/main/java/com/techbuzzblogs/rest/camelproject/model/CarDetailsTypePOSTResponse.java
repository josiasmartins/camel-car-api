package com.techbuzzblogs.rest.camelproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDetailsTypePOSTResponse {

    private Long Id;
    private String carName;
    private String carModel;
    private String company;
    private String isYou;

}
