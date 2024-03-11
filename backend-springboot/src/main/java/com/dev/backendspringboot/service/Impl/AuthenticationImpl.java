package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.repository.UserRepository;
import com.dev.backendspringboot.service.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements Authentication {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDocument signUp(SignUpRequest signUpRequest) {
        UserDocument user = UserDocument.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }
}
