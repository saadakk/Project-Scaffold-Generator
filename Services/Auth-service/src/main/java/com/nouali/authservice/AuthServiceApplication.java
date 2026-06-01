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
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner printProps(Environment env) {
        return args -> {
            System.out.println(">>> jwt.secret = " + env.getProperty("jwt.secret"));
            System.out.println(">>> jwt.access.expiration = " + env.getProperty("jwt.access.expiration"));
            System.out.println(">>> db.url = " + env.getProperty("db.url"));
        };
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()  // ← add this
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }*/

    @Bean
    CommandLineRunner run(UserServiceImpl userService, UserRepository userservice, RoleRepository roleRepository, RolesServiceImpl rolesService, UserRoleRepository userRoleRepository) {
        return args -> {








            System.out.println("=============================================");

            System.out.println("=============================================");
        };
    };

};
