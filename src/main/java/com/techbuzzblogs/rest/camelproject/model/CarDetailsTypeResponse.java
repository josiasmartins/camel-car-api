package com.techbuzzblogs.rest.camelproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDetailsTypeResponse {

    private Long id;
    private String carName;
    private String carModel;
    private String company;
    private String isYou;

}
