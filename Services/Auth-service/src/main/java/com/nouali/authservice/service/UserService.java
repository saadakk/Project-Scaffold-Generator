package com.nouali.authservice.service;

import com.nouali.authservice.entities.Users;

import java.util.Optional;

public interface UserService{
    Optional<Users> save (Users user);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByPhone(String phone);
}
