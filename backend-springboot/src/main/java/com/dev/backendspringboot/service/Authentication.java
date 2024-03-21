package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.LogInRequest;
import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.jwt.JwtAuthenticationResponse;
import com.dev.backendspringboot.jwt.Token;
import com.dev.backendspringboot.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface Authentication {
    CustomUserDetails signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse logIn(LogInRequest logInRequest);

    JwtAuthenticationResponse refreshToken(Token token);

    CustomUserDetails getProfile(HttpServletRequest request);
}
