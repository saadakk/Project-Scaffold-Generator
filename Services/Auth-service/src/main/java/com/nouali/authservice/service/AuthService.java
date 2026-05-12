package com.nouali.authservice.service;

import com.nouali.authservice.dto.AuthResponse;
import com.nouali.authservice.dto.LoginRequest;
import com.nouali.authservice.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse Register(RegisterRequest request);
}
