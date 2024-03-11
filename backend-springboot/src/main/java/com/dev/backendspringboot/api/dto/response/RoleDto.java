package com.dev.backendspringboot.api.dto.response;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleDto {
    private String id;
    private String name;
    private List<String> privileges;
    private List<UserRef> users;
}
