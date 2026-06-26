package com.nouali.authservice.service.Impl;

import com.nouali.authservice.entities.UserRole;
import com.nouali.authservice.repository.UserRoleRepository;
import com.nouali.authservice.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRolesService;
    public UserRoleServiceImpl(UserRoleRepository userRoles) {
        this.userRolesService = userRoles;
    }
    @Override
    public void saveUserRole(UserRole userRole) {
        userRolesService.save(userRole);
    }
}
