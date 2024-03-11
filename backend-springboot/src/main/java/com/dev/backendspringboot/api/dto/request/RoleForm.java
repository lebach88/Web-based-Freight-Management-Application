package com.dev.backendspringboot.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleForm {
    private String name;
    private List<String> privileges;
    private List<String> users;
}
