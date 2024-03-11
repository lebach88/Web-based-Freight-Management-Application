package com.dev.backendspringboot.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class MessageApi {
    private String message;
    private ZonedDateTime timestamp;
}
