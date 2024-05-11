package com.logihub.controller;

import com.logihub.model.response.LoginResponse;
import com.logihub.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/api/login")
    public LoginResponse login(Authentication authentication) {
        return authService.login(authentication);
    }
}
