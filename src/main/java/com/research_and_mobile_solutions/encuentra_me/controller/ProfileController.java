package com.research_and_mobile_solutions.encuentra_me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.model.Profile;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.resource.ProfileResource;
import com.research_and_mobile_solutions.encuentra_me.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        User user = profile.getUser();
        ProfileResource resource = new ProfileResource();
        resource.setId(profile.getId());
        resource.setFirstName(profile.getFirstName());
        resource.setPaternalLastName(profile.getPaternalLastName());
        resource.setMaternalLastName(profile.getMaternalLastName());
        resource.setDocumentType(profile.getDocumentType());
        resource.setDocumentNumber(profile.getDocumentNumber());
        resource.setBirthDate(profile.getBirthDate());
        resource.setCountryCode(profile.getCountryCode());
        resource.setPhone(profile.getPhone());
        resource.setUbigeo(profile.getUbigeo());
        if (user != null) {
            resource.setEmail(user.getEmail());
            if (user.getRole() != null) {
                resource.setRoleName(user.getRole().getName());
            }
        }
        return ResponseEntity.ok(resource);
    }

}
