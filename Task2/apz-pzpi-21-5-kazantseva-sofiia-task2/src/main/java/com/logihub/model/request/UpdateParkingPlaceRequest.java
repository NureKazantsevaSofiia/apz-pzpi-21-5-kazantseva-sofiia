package com.logihub.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateParkingPlaceRequest {

    @NotNull
    @Positive
    private Double minWidth;

    @NotNull
    @Positive
    private Double minHeight;

    @NotNull
    @Positive
    private Double minLength;

    @NotNull
    @Positive
    private Double minWeight;

    @NotNull
    @Positive
    private Double maxWidth;

    @NotNull
    @Positive
    private Double maxHeight;

    @NotNull
    @Positive
    private Double maxLength;

    @NotNull
    @Positive
    private Double maxWeight;

    @NotNull
    @Positive
    private Double hourlyPay;
}
