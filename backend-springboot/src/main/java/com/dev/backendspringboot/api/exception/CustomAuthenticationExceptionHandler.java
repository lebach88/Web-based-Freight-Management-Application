package com.dev.backendspringboot.api.exception;

import com.dev.backendspringboot.api.error.IncorrectPasswordException;
import com.dev.backendspringboot.api.error.UserAlreadyExistException;
import com.dev.backendspringboot.api.error.UsernameNotFoundException;
import com.dev.backendspringboot.jwt.JwtConstant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomAuthenticationExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class , UsernameNotFoundException.class})
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        problemDetail.setDetail("Username or password not found");
        problemDetail.setProperty("timestamp",ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
    @ExceptionHandler({UserAlreadyExistException.class, IncorrectPasswordException.class })
    public ProblemDetail handleUserAlreadyExist(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409),ex.getMessage());
        problemDetail.setProperty("access_denied_reason", "Authentication Failure");
        problemDetail.setProperty("timestamp", ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
    @ExceptionHandler({
            SignatureException.class, DecodingException.class, ExpiredJwtException.class,
            IllegalArgumentException.class, MalformedJwtException.class
    })
    public ProblemDetail handleSignatureException(RuntimeException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
        if (ex instanceof SignatureException){
            problemDetail.setProperty("access_denied_reason", JwtConstant.JWT_SIGNATURE);
        } else if (ex instanceof DecodingException){
            problemDetail.setProperty("access_denied_reason",JwtConstant.JWT_DECODE);
        }else if (ex instanceof ExpiredJwtException){
            problemDetail.setProperty("access_denied_reason",JwtConstant.JWT_EXPIRED);
        } else if (ex instanceof IllegalArgumentException) {
            problemDetail.setProperty("access_denied_reason",JwtConstant.JWT_EMPTY);
        }else if (ex instanceof MalformedJwtException){
            problemDetail.setProperty("access_denied_reason",JwtConstant.JWT_INVALID);
        }
        problemDetail.setProperty("timestamp", ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return problemDetail;
    }
}
