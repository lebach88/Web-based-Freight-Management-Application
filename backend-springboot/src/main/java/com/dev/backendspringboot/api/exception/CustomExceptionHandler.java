package com.dev.backendspringboot.api.exception;

import com.dev.backendspringboot.api.error.ApiError;
import com.dev.backendspringboot.api.error.RoleNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RoleNotFoundException.class)
    public ApiError handlerRoleNotFoundException(RoleNotFoundException ex, WebRequest request){
        ApiError apiError = new ApiError();
        apiError.setStatusCode(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return apiError;
    }
    @ExceptionHandler(ServerErrorException.class)
    public ProblemDetail handlerRuntimeException(ServerErrorException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),ex.getMessage());
        problemDetail.setDetail(ex.getMessage());
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        problemDetail.setProperty("timestamp", timestamp);
        return problemDetail;
    }
}
