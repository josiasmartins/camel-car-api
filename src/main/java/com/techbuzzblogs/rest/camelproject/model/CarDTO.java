package com.techbuzzblogs.rest.camelproject.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarDTO {
    private String carName;
    private String carModel;
    private String company;
    private Boolean isNews;
}
