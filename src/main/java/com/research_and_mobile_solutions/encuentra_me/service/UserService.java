package com.research_and_mobile_solutions.encuentra_me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.research_and_mobile_solutions.encuentra_me.dto.RegisterRequest;
import com.research_and_mobile_solutions.encuentra_me.model.Role;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Role role = roleService.findById(request.getRoleId());
        
        User user = User.builder()
            .email(request.getEmail())
            .password(encodedPassword)
            .role(role)
            .build();

        
        // Create profile for the user
        profileService.registerProfile(request, user);
        
        return userRepository.save(user);
    }
}
