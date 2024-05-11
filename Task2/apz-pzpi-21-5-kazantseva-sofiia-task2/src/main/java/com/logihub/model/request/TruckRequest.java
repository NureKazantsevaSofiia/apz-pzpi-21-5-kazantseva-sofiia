package com.logihub.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TruckRequest {

    @NotNull
    private String number;

    @NotNull
    private Double width;

    @NotNull
    private Double height;

    @NotNull
    private Double length;

    @NotNull
    private Double weight;
}
