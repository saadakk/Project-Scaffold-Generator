package com.nouali.authservice.service.Impl;
import com.nouali.authservice.dto.AuthResponse;
import com.nouali.authservice.dto.LoginRequest;
import com.nouali.authservice.dto.RegisterRequest;
import com.nouali.authservice.entities.Role;
import com.nouali.authservice.entities.UserRole;
import com.nouali.authservice.entities.Users;
import com.nouali.authservice.security.JwtService;
import com.nouali.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final RolesServiceImpl rolesService;
    private final UserRoleServiceImpl userRoleService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Value("${role.utilisateur}")
    private String roleUtilisateur;
    public AuthServiceImpl(UserServiceImpl userService, RolesServiceImpl rolesService, UserRoleServiceImpl userRoleService, JwtService jwtServic, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManage, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.rolesService = rolesService;
        this.userRoleService = userRoleService;
        this.jwtService = jwtServic;
        this.refreshTokenService = refreshTokenService;

        this.authenticationManager = authenticationManage;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return new AuthResponse(accessToken, refreshToken);
    }
    @Override
    public AuthResponse Register(RegisterRequest request) {

        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = rolesService.getRoleByName(roleUtilisateur)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Users savedUser = userService.save(user)
                .orElseThrow(() -> new RuntimeException("User not saved"));
        UserRole userRole = new UserRole();
        userRole.setUser_roles(savedUser);
        userRole.setRole(role);
        userRoleService.saveUserRole(userRole);

        String accessToken = jwtService.generateToken(savedUser.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }
}
