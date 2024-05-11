package com.logihub.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateAdminRequest {

    private String avatar;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 24)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 24)
    private String lastName;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 24)
    private String password;
}