package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.LogInRequest;
import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.jwt.JwtAuthenticationResponse;
import com.dev.backendspringboot.jwt.Token;
import com.dev.backendspringboot.security.CustomUserDetails;
import com.dev.backendspringboot.service.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private Authentication authentication;
    @Autowired
    public AuthController(Authentication authentication) {
        this.authentication = authentication;
    }
    @PostMapping("/signup")
    public ResponseEntity<CustomUserDetails> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authentication.signUp(signUpRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(authentication.logIn(logInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody Token token) {
        return ResponseEntity.ok(authentication.refreshToken(token));
    }
    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> profile(HttpServletRequest request){
        return ResponseEntity.ok(authentication.getProfile(request));
    }
}
