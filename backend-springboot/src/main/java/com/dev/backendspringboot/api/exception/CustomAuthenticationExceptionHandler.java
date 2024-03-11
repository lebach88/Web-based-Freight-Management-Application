package com.dev.backendspringboot.api.exception;

import com.dev.backendspringboot.api.error.UsernameNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomAuthenticationExceptionHandler {
    @ExceptionHandler({UsernameNotFoundException.class})
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        errorDetail.setDetail("Username or password not found");
        errorDetail.setProperty("timestamp",ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return errorDetail;
    }
}
