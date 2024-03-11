package com.dev.backendspringboot.api.dto.ref;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRef {
    private String id;
    private String email;
}
