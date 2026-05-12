package com.nouali.authservice.service.Impl;

import com.nouali.authservice.entities.Role;
import com.nouali.authservice.repository.RoleRepository;
import com.nouali.authservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RolesServiceImpl implements RoleService {
    private  final RoleRepository roleRepository;

    public RolesServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}
