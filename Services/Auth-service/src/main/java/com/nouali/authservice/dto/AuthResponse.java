package com.nouali.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;   // access token uniquement — le refresh token part en httpOnly cookie
}
