package com.nouali.authservice.controller;

import com.nouali.authservice.dto.AuthResponse;
import com.nouali.authservice.dto.LoginRequest;
import com.nouali.authservice.dto.RegisterRequest;
import com.nouali.authservice.security.CustomUserDetailsService;
import com.nouali.authservice.service.Impl.AuthServiceImpl;
import com.nouali.authservice.service.Impl.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl          authService;
    private final RefreshTokenService      refreshTokenService;
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration; // en millisecondes

    public AuthController(AuthServiceImpl authService,
                          RefreshTokenService refreshTokenService,
                          CustomUserDetailsService userDetailsService) {
        this.authService         = authService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService  = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request,
                                                 HttpServletResponse response) {
        AuthResponse body = authService.Register(request);
        String refreshToken = refreshTokenService.get(request.getEmail());
        setRefreshCookie(response, request.getEmail(), refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        AuthResponse body = authService.login(request);
        String refreshToken = refreshTokenService.get(request.getEmail());
        setRefreshCookie(response, request.getEmail(), refreshToken);
        return ResponseEntity.ok(body);
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request,
                                                HttpServletResponse response) {
        String cookieValue = extractCookie(request, "refresh_token");
        if (cookieValue == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String[] parts = cookieValue.split("\\|", 2);
        if (parts.length != 2) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email        = parts[0];
        String refreshToken = parts[1];

        try {
            AuthResponse body = authService.refresh(email, refreshToken);
            String newRefreshToken = refreshTokenService.get(email);
            setRefreshCookie(response, email, newRefreshToken);
            return ResponseEntity.ok(body);
        } catch (RuntimeException e) {
            clearRefreshCookie(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response) {
        String cookieValue = extractCookie(request, "refresh_token");
        if (cookieValue != null) {
            String email = cookieValue.split("\\|", 2)[0];
            refreshTokenService.delete(email);
        }
        clearRefreshCookie(response);
        return ResponseEntity.noContent().build();
    }


    private void setRefreshCookie(HttpServletResponse response, String email, String refreshToken) {
        int maxAge = (int) (refreshExpiration / 1000);
        String value = email + "|" + refreshToken;
        response.addHeader("Set-Cookie", String.format(
                "refresh_token=%s; HttpOnly; Path=/; Max-Age=%d; SameSite=Strict",
                value, maxAge
        ));
    }

    private void clearRefreshCookie(HttpServletResponse response) {
        response.addHeader("Set-Cookie",
                "refresh_token=%s; HttpOnly; Path=/; Max-Age=%d; SameSite=Strict");
    }
    private String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(jakarta.servlet.http.Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
