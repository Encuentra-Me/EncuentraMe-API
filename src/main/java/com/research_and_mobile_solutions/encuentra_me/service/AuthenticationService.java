package com.research_and_mobile_solutions.encuentra_me.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationRequest;
import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationResponse;
import com.research_and_mobile_solutions.encuentra_me.dto.RegisterRequest;
import com.research_and_mobile_solutions.encuentra_me.model.Profile;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.repository.IProfileRepository;
import com.research_and_mobile_solutions.encuentra_me.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Create and save user
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        // Create and save profile
        var profile = Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .user(user)
                .build();
        profileRepository.save(profile);

        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }
} 