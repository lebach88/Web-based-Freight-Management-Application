package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import com.dev.backendspringboot.api.dto.request.RoleForm;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.RoleDto;
import com.dev.backendspringboot.document.Role;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.repository.RoleRepository;
import com.dev.backendspringboot.repository.UserRepository;
import com.dev.backendspringboot.service.RoleService;
import com.dev.backendspringboot.service.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev.backendspringboot.service.util.RoleUtil.*;

/**
 * Lớp RoleServiceImpl thực hiện các thao tác liên quan đến Role trong ứng dụng.
 */
@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private RoleUtil roleUtil;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,UserRepository userRepository, RoleUtil roleUtil) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleUtil = roleUtil;
    }

    @Override
    public RoleDto findOneRoleById(String roleId) {
        Role role = roleRepository.findById(roleId).get();
        List<UserRef> userRefs;
        if (role.getUsers() != null) {
            userRefs = role.getUsers().stream()
                    .map(user -> UserRef.builder().id(user.getId()).email(user.getEmail()).build())
                    .collect(Collectors.toList());
        } else {
            userRefs = new ArrayList<>();
        }
        return roleUtil.getRoleDto(role, userRefs);
    }


    @Override
    public List<RoleDto> findAllRole() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> findOneRoleById(role.getId())).collect(Collectors.toList());
    }

    @Override
    public RoleDto createNewRole(RoleForm roleForm) {
        List<String> privileges = roleUtil.getPrivileges(roleForm.getPrivileges());
        Role newRole = roleUtil.saveRole(roleForm, privileges);
        roleUtil.addRolesToUser(roleForm.getUsers(),newRole);
        return findOneRoleById(newRole.getId());
    }

    @Override
    public RoleDto updateRole(String roleId, RoleForm roleForm) {
        RoleDto roleDto = findOneRoleById(roleId);
        roleDto.setName(roleForm.getName());
        List<String> privileges = roleUtil.getPrivileges(roleForm.getPrivileges());
        roleDto.setPrivileges(privileges);
        Role role = Role.builder()
                .name(roleDto.getName())
                .privileges(roleDto.getPrivileges())
                .users(roleUtil.getUserDocument(roleDto.getUsers()))
                .build();
        Role roleUpdate = roleRepository.save(role);
        roleUtil.addRolesToUser(roleForm.getUsers(),roleUpdate);
        return findOneRoleById(roleId);
    }

    @Override
    public MessageApi deleteOneRole(String roleId) {
        RoleDto roleDto = findOneRoleById(roleId);
        if (roleDto.getUsers() != null){
            for (UserRef userRef : roleDto.getUsers()){
                UserDocument user = userRepository.findById(userRef.getId()).get();
                List<Role> roles = user.getRoles();
                if (roles != null){
                    roles.removeIf(role -> role.getId().equals(roleId));
                    user.setRoles(roles);
                    userRepository.save(user);
                }
            }
        }
        roleRepository.deleteById(roleId);
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        MessageApi message = MessageApi.builder()
                .message("deleted successfully")
                .timestamp(timestamp)
                .build();
        return message;
    }

    @Override
    public MessageApi deleteAllRole() {
        List<UserDocument> users = userRepository.findAll();
        for (UserDocument user : users) {
            user.setRoles(new ArrayList<>());
            userRepository.save(user);
        }
        roleRepository.deleteAll();
        MessageApi message = MessageApi.builder().message("deleted all successfully").timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))).build();
        return message;
    }
}
