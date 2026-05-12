package com.nouali.authservice;

import com.nouali.authservice.entities.Role;
import com.nouali.authservice.entities.UserRole;
import com.nouali.authservice.entities.Users;
import com.nouali.authservice.repository.RoleRepository;
import com.nouali.authservice.repository.UserRepository;

import com.nouali.authservice.repository.UserRoleRepository;
import com.nouali.authservice.security.CustomUserDetailsService;
import com.nouali.authservice.service.Impl.RolesServiceImpl;
import com.nouali.authservice.service.Impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceImpl userService, UserRepository userservice, RoleRepository roleRepository, RolesServiceImpl rolesService, UserRoleRepository userRoleRepository) {
        return args -> {








            System.out.println("=============================================");

            System.out.println("=============================================");
        };
    };

};
