package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.service.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private Authentication authentication;
    @Autowired
    public AuthController(Authentication authentication) {
        this.authentication = authentication;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDocument> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authentication.signUp(signUpRequest));
    }
}
