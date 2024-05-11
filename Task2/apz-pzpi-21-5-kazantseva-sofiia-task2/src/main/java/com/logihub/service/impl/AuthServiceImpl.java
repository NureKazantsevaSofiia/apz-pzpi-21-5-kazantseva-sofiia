package com.logihub.service.impl;

import com.logihub.model.response.LoginResponse;
import com.logihub.repository.UserRepository;
import com.logihub.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private JwtEncoder jwtEncoder;

    @Override
    public LoginResponse login(Authentication authentication) {
        return getLoginResponse(authentication.getName());

    }

    private LoginResponse getLoginResponse(String email) {
        var user = userRepository.findByEmailIgnoreCase(email).get();
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .subject(email)
                .claim("id", user.getId())
                .claim("role", user.getRole().name().toLowerCase())
                .build();

        log.error("Logged in = {}", email);

        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .name(user.getFirstName())
                .surname(user.getLastName())
                .role(user.getRole().name().toLowerCase())
                .avatar(user.getAvatar())
                .build();
    }
}
