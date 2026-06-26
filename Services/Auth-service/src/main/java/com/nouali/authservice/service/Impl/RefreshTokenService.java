package com.nouali.authservice.service.Impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_TTL_DAYS = 7;

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(String email) {
        return "refresh:" + email;
    }

    public String createAndSave(String email) {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key(email), token, REFRESH_TTL_DAYS, TimeUnit.DAYS);
        return token;
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
}
