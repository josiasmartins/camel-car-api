package com.techbuzzblogs.rest.camelproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDetailsTypePOSTResponse {

    private Long Id;
    private String carName;
    private String carModel;
    private String company;
//    private String isYou;

}
