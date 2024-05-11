package com.logihub.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private Long id;

    private String token;

    private String name;

    private String surname;

    private String role;

    private String avatar;
}
