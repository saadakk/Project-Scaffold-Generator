package com.nouali.authservice.service.Impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(String email) {
        return "refresh:" + email;
    }

    public void save(String email, String refreshToken) {
        redisTemplate.opsForValue().set(
                key(email),
                refreshToken,
                7,
                TimeUnit.DAYS
        );
    }


    public String get(String email) {
        return redisTemplate.opsForValue().get(key(email));
    }


    public void delete(String email) {
        redisTemplate.delete(key(email));
    }


    public boolean isValid(String email, String token) {
        String stored = get(email);
        return stored != null && stored.equals(token);
    }

    public String createRefreshToken(String email) {

        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                "refresh:" + email,
                token,
                7,
                TimeUnit.DAYS
        );

        return token;
    }

}