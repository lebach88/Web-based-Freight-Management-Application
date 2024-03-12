package com.dev.backendspringboot.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;

public interface JwtService {
    Token generateToken(UserDetails userDetails);
    Token generateRefreshToken(Map<String, Objects> extraClaims, UserDetails userDetails);

    String extraUsername(String token);

    boolean isValidToken(String token, UserDetails userDetails);
}
