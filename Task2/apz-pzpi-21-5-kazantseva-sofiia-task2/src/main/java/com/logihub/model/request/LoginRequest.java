package com.logihub.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty
    @NotNull
    @Email
    private String username;

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 24)
    private String password;
}