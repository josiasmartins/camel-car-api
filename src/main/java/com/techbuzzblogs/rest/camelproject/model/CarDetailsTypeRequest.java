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
public class CarDetailsTypeRequest {

    private Long id;
    private String carName;
    private String carModel;
    private String company;

}
