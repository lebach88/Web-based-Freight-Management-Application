package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.RoleForm;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto findOneRoleById(String roleId);

    List<RoleDto> findAllRole();

    RoleDto createNewRole(RoleForm roleForm);

    MessageApi deleteAllRole();

    RoleDto updateRole(String roleId, RoleForm roleForm);

    MessageApi deleteOneRole(String roleId);
}
