package com.research_and_mobile_solutions.encuentra_me.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationRequest;
import com.research_and_mobile_solutions.encuentra_me.dto.AuthenticationResponse;
import com.research_and_mobile_solutions.encuentra_me.dto.RegisterRequest;
import com.research_and_mobile_solutions.encuentra_me.model.User;
import com.research_and_mobile_solutions.encuentra_me.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Create and save user
        User user = userService.registerUser(request);
        
        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .build();
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw e; // Let Spring Security handle the authentication exception
        } catch (Exception e) {
            throw new RuntimeException("Internal server error during authentication", e);
        }
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public void logout(String token) {
        // In a real implementation, you might want to:
        // 1. Add the token to a blacklist
        // 2. Clear any server-side sessions
        // 3. Invalidate refresh tokens
        // For now, we'll just return a success response
        // The client should remove the token from storage
    }
} 