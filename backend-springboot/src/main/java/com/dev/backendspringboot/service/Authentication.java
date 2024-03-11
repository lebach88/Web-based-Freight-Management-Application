package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.SignUpRequest;
import com.dev.backendspringboot.document.UserDocument;

public interface Authentication {
    UserDocument signUp(SignUpRequest signUpRequest);
}
