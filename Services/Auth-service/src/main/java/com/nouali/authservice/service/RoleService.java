package com.nouali.authservice.service;

import com.nouali.authservice.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleByName(String name);
}
