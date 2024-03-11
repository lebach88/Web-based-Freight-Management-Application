package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.RoleForm;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.RoleDto;
import com.dev.backendspringboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Lớp RoleController xử lý các yêu cầu HTTP liên quan đến vai trò và quyền truy cập trong ứng dụng
 */
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> findAllRole(){
        return ResponseEntity.ok(roleService.findAllRole());
    }
    @PostMapping("/")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleForm roleForm){
        return ResponseEntity.ok(roleService.createNewRole(roleForm));
    }
    @DeleteMapping("/")
    public ResponseEntity<MessageApi> deleteAllRole(){
        return ResponseEntity.ok(roleService.deleteAllRole());
    }
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> findOneRoleById(@PathVariable String roleId){
        return ResponseEntity.ok(roleService.findOneRoleById(roleId));
    }
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable String roleId, @RequestBody RoleForm roleForm){
        return ResponseEntity.ok(roleService.updateRole(roleId, roleForm));
    }
    @DeleteMapping("/{roleId}")
    public ResponseEntity<MessageApi> deleteOneRole(@PathVariable String roleId){
        return ResponseEntity.ok(roleService.deleteOneRole(roleId));
    }
}
