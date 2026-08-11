package com.fernando84.authservice.service;

import com.fernando84.authservice.dto.AuthResponse;
import com.fernando84.authservice.dto.LoginRequest;
import com.fernando84.authservice.dto.RegisterRequest;
import com.fernando84.authservice.exception.InvalidCredentialsException;
import com.fernando84.authservice.exception.UsernameAlreadyExistsException;
import com.fernando84.authservice.model.AppUser;
import com.fernando84.authservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.findByUsername(request.username()).isPresent()) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        appUserRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByUsername(request.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new AuthResponse(jwtService.generateToken(user));
    }
}