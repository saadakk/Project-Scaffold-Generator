package com.nouali.authservice.controller;

import com.nouali.authservice.dto.AuthResponse;
import com.nouali.authservice.dto.LoginRequest;
import com.nouali.authservice.dto.RegisterRequest;
import com.nouali.authservice.repository.UserRepository;
import com.nouali.authservice.security.CustomUserDetailsService;
import com.nouali.authservice.service.Impl.AuthServiceImpl;

import com.nouali.authservice.service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthServiceImpl authService, CustomUserDetailsService userDetailsService) {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.Register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}