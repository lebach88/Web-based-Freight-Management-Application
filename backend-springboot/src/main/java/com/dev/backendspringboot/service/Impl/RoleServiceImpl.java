package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import com.dev.backendspringboot.api.dto.request.RoleForm;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.RoleDto;
import com.dev.backendspringboot.api.error.RoleNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.api.error.UsernameNotFoundException;
import com.dev.backendspringboot.document.Role;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.repository.RoleRepository;
import com.dev.backendspringboot.repository.UserRepository;
import com.dev.backendspringboot.service.RoleService;
import com.dev.backendspringboot.service.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Could not find role with id : "+ roleId));
        try {
            List<UserRef> userRefs;
            if (role.getUsers() != null) {
                userRefs = role.getUsers().stream()
                        .map(user -> UserRef.builder().id(user.getId()).email(user.getEmail()).build())
                        .collect(Collectors.toList());
            } else {
                userRefs = new ArrayList<>();
            }
            return roleUtil.getRoleDto(role, userRefs);
        }catch (Exception ex) {
            throw new ServerErrorException("Đã xảy ra lỗi khi tìm một role", ex);
        }
    }


    @Override
    public List<RoleDto> findAllRole() {
        try {
            List<Role> roles = roleRepository.findAll();
            return roles.stream().map(role -> findOneRoleById(role.getId())).collect(Collectors.toList());
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tìm tất cả role",ex);
        }
    }

    @Override
    public RoleDto createNewRole(RoleForm roleForm) {
        try {
            List<String> privileges = roleUtil.getPrivileges(roleForm.getPrivileges());
            Role newRole = roleUtil.saveRole(roleForm, privileges);
            roleUtil.addRolesToUser(roleForm.getUsers(),newRole);
            return findOneRoleById(newRole.getId());
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tạo role mới ", ex);
        }

    }
    @Override
    public RoleDto updateRole(String roleId, RoleForm roleForm) {
        RoleDto roleDto = findOneRoleById(roleId);
        try {
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
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi cập nhật role", ex);
        }
    }

    @Override
    public MessageApi deleteOneRole(String roleId) {
        RoleDto roleDto = findOneRoleById(roleId);
        try {
            if (roleDto.getUsers() != null){
                for (UserRef userRef : roleDto.getUsers()){
                    UserDocument user = userRepository.findById(userRef.getId())
                            .orElseThrow(() -> new UsernameNotFoundException("There is not an account with id : "+ userRef.getId()));
                    List<Role> roles = user.getRoles();
                    if (roles != null){
                        roles.removeIf(role -> role.getId().equals(roleId));
                        user.setRoles(roles);
                        userRepository.save(user);
                    }
                }
            }
            roleRepository.deleteById(roleId);
            return roleUtil.getMessageApi("deleted successfully");
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa một role ", ex);
        }
    }

    @Override
    public MessageApi deleteAllRole() {
        try {
            List<UserDocument> users = userRepository.findAll();
            for (UserDocument user : users) {
                user.setRoles(new ArrayList<>());
                userRepository.save(user);
            }
            roleRepository.deleteAll();
            return roleUtil.getMessageApi("deleted all successfully");
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa tất cả role ", ex);
        }
    }
}
