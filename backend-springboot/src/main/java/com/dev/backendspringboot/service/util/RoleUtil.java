package com.dev.backendspringboot.service.util;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import com.dev.backendspringboot.api.dto.request.RoleForm;
import com.dev.backendspringboot.api.dto.response.RoleDto;
import com.dev.backendspringboot.data.Privilege;
import com.dev.backendspringboot.document.Role;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.repository.RoleRepository;
import com.dev.backendspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Lớp RoleUtil thực hiện các thao tác liên quan đến xử lý dữ liệu trong ứng dụng.
 */
@Component
public class RoleUtil {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    @Autowired
    public RoleUtil(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public RoleDto getRoleDto(Role role, List<UserRef> userRefs) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .users(userRefs)
                .privileges(role.getPrivileges())
                .build();
    }
    public Role saveRole(RoleForm roleForm, List<String> privileges) {
        Role role = Role.builder()
                .name(roleForm.getName())
                .privileges(privileges)
                .users(getUsers(roleForm.getUsers()))
                .build();
        return roleRepository.save(role);
    }
    public void addRolesToUser(List<String> users, Role role) {
        if (users != null){
            for (String id : users){
                UserDocument user = userRepository.findById(id).get();
                List<Role> userRoles = user.getRoles();
                if (userRoles == null){
                    userRoles = new ArrayList<>();
                    user.setRoles(userRoles);
                }
                userRoles.add(role);
                userRepository.save(user);
            }
        }
    }

    public List<UserDocument> getUsers(List<String> users) {
        return users.stream().map(user -> userRepository.findById(user).get()).collect(Collectors.toList());
    }
    public List<String> getPrivileges(List<String> privileges) {
        List<String> stacks = new ArrayList<>();
        for (String privilege : privileges){
            try {
                Privilege privilegeEnum = Privilege.valueOf(privilege.toUpperCase());
                stacks.add(privilege.toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println(privilege + " không tồn tại trong enum Privilege");
            }
        }
        return stacks;
    }
    public List<UserDocument> getUserDocument(List<UserRef> users) {
        return users.stream().map(user -> userRepository.findByEmail(user.getEmail()).get()).collect(Collectors.toList());
    }
}
