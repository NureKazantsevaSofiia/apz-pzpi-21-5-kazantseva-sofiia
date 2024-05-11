package com.logihub.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 40)
    private String name;

    @NotNull
    @Positive
    private Double weight;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Long amount;
}
