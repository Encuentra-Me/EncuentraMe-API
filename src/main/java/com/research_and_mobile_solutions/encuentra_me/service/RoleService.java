package com.research_and_mobile_solutions.encuentra_me.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.research_and_mobile_solutions.encuentra_me.model.Role;
import com.research_and_mobile_solutions.encuentra_me.repository.IRoleRepository;

@Service
public class RoleService {

    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Role not found with name: " + name));
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public void saveDefault(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(Long.valueOf(1)).name("CIUDADANO").build());
        roles.add(Role.builder().id(Long.valueOf(2)).name("OPERADOR 114").build());
        roles.add(Role.builder().id(Long.valueOf(3)).name("POLICIA").build());
        roleRepository.saveAll(roles);
    }

} 