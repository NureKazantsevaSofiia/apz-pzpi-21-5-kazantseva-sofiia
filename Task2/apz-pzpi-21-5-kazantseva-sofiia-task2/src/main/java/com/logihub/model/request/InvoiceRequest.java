package com.logihub.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    @NotEmpty
    private String truckNumber;

    @NotNull
    @NotEmpty
    private String placeNumber;

    private List<ItemRequest> items;

    @NotNull
    @NotEmpty
    private String description;
}
