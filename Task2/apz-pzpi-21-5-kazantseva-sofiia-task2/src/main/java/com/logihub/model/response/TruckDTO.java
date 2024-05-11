package com.logihub.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TruckDTO {

    private Long id;

    private String number;

    private TruckManagerDTO truckManager;

    private Double width;

    private Double height;

    private Double length;

    private Double weight;
}
