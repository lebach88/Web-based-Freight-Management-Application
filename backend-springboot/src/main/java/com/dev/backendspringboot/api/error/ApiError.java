package com.dev.backendspringboot.api.error;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiError {
    private Integer statusCode;
    private String message;
    private ZonedDateTime timestamp;
}
