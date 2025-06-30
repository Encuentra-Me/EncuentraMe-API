package com.research_and_mobile_solutions.encuentra_me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.model.Profile;
import com.research_and_mobile_solutions.encuentra_me.model.Role;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.resource.UserResource;
import com.research_and_mobile_solutions.encuentra_me.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {

        //System.out.println("<<<<<<<<<<<<id: "+id);
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        //System.out.println("<<<<<<<<<<<<user: "+user.getId());

        UserResource resource = new UserResource();
        resource.setId(user.getId());
        resource.setEmail(user.getEmail());

        Profile profile = user.getProfile();
        if(profile != null){
            resource.setFirstName(profile.getFirstName());
            resource.setPaternalLastName(profile.getPaternalLastName());
            resource.setMaternalLastName(profile.getMaternalLastName());
            resource.setDocumentType(profile.getDocumentType());
            resource.setDocumentNumber(profile.getDocumentNumber());
            resource.setBirthDate(profile.getBirthDate());
            resource.setCountryCode(profile.getCountryCode());
            resource.setPhone(profile.getPhone());
            resource.setUbigeo(profile.getUbigeo());
        }

        Role role = user.getRole();
        if(role != null){
            resource.setRoleName(role.getName());
        }
        return ResponseEntity.ok(resource);
    }
}
