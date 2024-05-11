package com.logihub.service;

import com.logihub.model.response.LoginResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    LoginResponse login(Authentication auth);
}
