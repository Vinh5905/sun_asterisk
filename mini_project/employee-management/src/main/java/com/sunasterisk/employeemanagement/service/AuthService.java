package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.dto.auth.AuthResponse;
import com.sunasterisk.employeemanagement.dto.auth.LoginRequest;
import com.sunasterisk.employeemanagement.dto.auth.RegisterRequest;
import com.sunasterisk.employeemanagement.exception.BadRequestException;
import com.sunasterisk.employeemanagement.model.AppUser;
import com.sunasterisk.employeemanagement.model.Role;
import com.sunasterisk.employeemanagement.repository.AppUserRepository;
import com.sunasterisk.employeemanagement.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
        AppUserRepository appUserRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        JwtService jwtService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username already exists");
        }

        Role role = request.role() == null ? Role.USER : request.role();
        AppUser user = appUserRepository.save(new AppUser(
            request.username(),
            passwordEncoder.encode(request.password()),
            role
        ));

        return toAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.username(),
            request.password()
        ));

        AppUser user = appUserRepository.findByUsername(request.username())
            .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        return toAuthResponse(user);
    }

    private AuthResponse toAuthResponse(AppUser user) {
        return new AuthResponse(
            user.getUsername(),
            user.getRole().name(),
            jwtService.generateToken(user)
        );
    }
}
