package com.research_and_mobile_solutions.encuentra_me.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.model.Role;
import com.research_and_mobile_solutions.encuentra_me.resource.RoleResource;
import com.research_and_mobile_solutions.encuentra_me.resource.SaveRoleResource;
import com.research_and_mobile_solutions.encuentra_me.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper mapper;

    private Role convertToEntity(SaveRoleResource resource){
        return mapper.map(resource, Role.class);
    }
    private RoleResource convertToResource(Role entity){
        return mapper.map(entity, RoleResource.class);
    }

    @GetMapping
    public ResponseEntity<List<RoleResource>> getAllRoles() {

        List<Role> roles = roleService.getAllRoles();
        int total = roles.size();

        if (total == 0) {
            roleService.saveDefault();
            roles = roleService.getAllRoles();
        }

        List<RoleResource> resources = roles.stream()
                .map(this::convertToResource).collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
} 