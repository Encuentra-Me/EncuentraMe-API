package com.research_and_mobile_solutions.encuentra_me.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.research_and_mobile_solutions.encuentra_me.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Rutas públicas que no requieren validación de JWT
    private static final List<String> PUBLIC_PATHS = List.of(
        
         "/api/v1/reports",
        "/api/v1/reports/",
        "/api/v1/rekognition",
        "/api/v1/rekognition/",
        "/swagger-ui",
        "/swagger-ui/",
        "/swagger-ui/index.html",
        "/v3/api-docs",
        "/v3/api-docs/",
        "/swagger-resources",
        "/swagger-resources/",
        "/webjars/",
        "/error"
    
        /* "/api/v1/",
        "/api/auth/",
        "/api/v1/reports",
        "/api/v1/reports/",
        "/swagger-ui",
        "/swagger-ui/",
        "/v3/api-docs",
        "/v3/api-docs/",
        "/swagger-resources",
        "/swagger-resources/",
        "/webjars/",
        "/error"*/
    );

    @Override
protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
) throws ServletException, IOException {

    String path = request.getServletPath();
    System.out.println("🔍 Ruta solicitada: " + path); // ← para debug

    // Permitir todo /api/v1/reports/**
    /*if (path.startsWith("/api/v1/reports") || PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
        filterChain.doFilter(request, response);
        return;
    }

    // Ignorar rutas públicas
    if (PUBLIC_PATHS.contains(path)) {
        filterChain.doFilter(request, response);
        return;
    }*/
    
    /*if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
        filterChain.doFilter(request, response);
        return;
    }*/

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
}
} 