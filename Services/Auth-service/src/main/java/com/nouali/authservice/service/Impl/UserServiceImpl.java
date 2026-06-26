package com.nouali.authservice.service.Impl;

import com.nouali.authservice.entities.Users;
import com.nouali.authservice.repository.UserRepository;
import com.nouali.authservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<Users> save(Users user) {
       return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
