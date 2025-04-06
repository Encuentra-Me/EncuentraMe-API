package com.research_and_mobile_solutions.encuentra_me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.research_and_mobile_solutions.encuentra_me.repository.IUserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    IUserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        com.research_and_mobile_solutions.encuentra_me.model.User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return User.builder().username(user.getEmail()).password(user.getPassword()).build();
    }

    public com.research_and_mobile_solutions.encuentra_me.model.User registerUser(String email, String rawPassword){
        String encodedPassword = passwordEncoder.encode(rawPassword);
        com.research_and_mobile_solutions.encuentra_me.model.User user = new com.research_and_mobile_solutions.encuentra_me.model.User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        return userRepository.save(user);

    }
}
