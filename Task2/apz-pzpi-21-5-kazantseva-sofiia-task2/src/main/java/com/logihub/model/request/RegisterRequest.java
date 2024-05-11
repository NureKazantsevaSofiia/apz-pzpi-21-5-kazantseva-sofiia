package com.logihub.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

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
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 24)
    private String password;

    @NotNull
    private Long companyId;
}