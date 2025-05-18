package com.research_and_mobile_solutions.encuentra_me.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationRequest;
import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationResponse;
import com.research_and_mobile_solutions.encuentra_me.dto.RegisterRequest;
import com.research_and_mobile_solutions.encuentra_me.service.AuthenticationService;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        System.out.println(request.getFirstName());
        System.out.println(request.getPaternalLastName());
        System.out.println(request.getMaternalLastName());
        System.out.println(request.getDocumentType());
        System.out.println(request.getDocumentNumber());
        System.out.println(request.getBirthDate());
        System.out.println(request.getCountryCode());
        System.out.println(request.getPhone());
        System.out.println(request.getUbigeo());
        System.out.println(request.getRoleId());
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailAvailability(
            @RequestParam @Email String email
    ) {
        boolean isAvailable = service.isEmailAvailable(email);  
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            service.logout(token);
        }
        return ResponseEntity.ok().build();
    }
} 