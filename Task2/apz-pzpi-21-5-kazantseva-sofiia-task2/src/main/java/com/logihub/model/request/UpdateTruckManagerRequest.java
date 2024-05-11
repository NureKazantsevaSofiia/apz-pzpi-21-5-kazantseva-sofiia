package com.logihub.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTruckManagerRequest {

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 24)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 24)
    private String lastName;

    private String avatar;
}
