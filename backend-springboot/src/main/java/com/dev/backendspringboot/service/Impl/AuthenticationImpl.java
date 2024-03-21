package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.LogInRequest;
import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.api.error.IncorrectPasswordException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.api.error.UserAlreadyExistException;
import com.dev.backendspringboot.document.Role;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.jwt.JwtAuthenticationResponse;
import com.dev.backendspringboot.jwt.JwtService;
import com.dev.backendspringboot.jwt.Token;
import com.dev.backendspringboot.repository.RoleRepository;
import com.dev.backendspringboot.repository.UserRepository;
import com.dev.backendspringboot.security.CustomUserDetails;
import com.dev.backendspringboot.service.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationImpl implements Authentication {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private JwtService jwtService;
    private AuthenticationManager manager;
    @Autowired
    public AuthenticationImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              RoleRepository roleRepository,
                              JwtService jwtService, AuthenticationManager manager)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.manager =manager;
    }
    @Override
    public CustomUserDetails signUp(SignUpRequest signUpRequest) {
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())){
                throw new UserAlreadyExistException("There is an account with that email address: " + signUpRequest.getEmail());
            }
            if (!Objects.equals(signUpRequest.getPassword(),signUpRequest.getPasswordConfirm())){
                throw new IncorrectPasswordException("Passwords are not the same");
            }
            if (!roleRepository.existsByName("USER")){
                List<String> privileges = Collections.singletonList("READ");
                Role roleCreate = Role.builder()
                        .name("USER")
                        .privileges(privileges)
                        .build();
                roleRepository.save(roleCreate);
            }
            Role role = roleRepository.findByName("USER").orElseThrow();
            UserDocument user = UserDocument.builder()
                    .firstName(signUpRequest.getFirstName())
                    .lastName(signUpRequest.getLastName())
                    .email(signUpRequest.getEmail())
                    .isEnabled(true)
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(Collections.singletonList(role))
                    .build();
            userRepository.save(user);

            role.setUsers(Collections.singletonList(user));
            roleRepository.save(role);

            return new CustomUserDetails(user);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi đăng ký tài khoản ", ex);
        }
    }

    @Override
    public JwtAuthenticationResponse logIn(LogInRequest logInRequest) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getEmail(),logInRequest.getPassword()));
            UserDocument user = userRepository.findByEmail(logInRequest.getEmail()).orElseThrow();

            var jwt = jwtService.generateToken(new CustomUserDetails(user));
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),new CustomUserDetails(user));
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt.getToken());
            jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
            return jwtAuthenticationResponse;
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi đăng nhập ", ex);
        }
    }

    @Override
    public JwtAuthenticationResponse refreshToken(Token token) {
        try {
            final String userEmail = jwtService.extraUsername(token.getToken());
            UserDocument user = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isValidToken(token.getToken() , new CustomUserDetails(user))){
                var jwt =jwtService.generateToken(new CustomUserDetails(user));
                JwtAuthenticationResponse response = new JwtAuthenticationResponse();
                response.setToken(jwt.getToken());
                response.setRefreshToken(token.getToken());
                return response;
            }
            return null;
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy lại token ", ex);
        }
    }

    @Override
    public CustomUserDetails getProfile(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return new CustomUserDetails(getUserDocument(request));
    }

    private UserDocument getUserDocument(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.length() <= 7) {
            return null;
        }
        String token = authHeader.substring(7);
        String email = jwtService.extraUsername(token);
        Optional<UserDocument> userDocument = userRepository.findByEmail(email);
        return userDocument.orElse(null);
    }

}
