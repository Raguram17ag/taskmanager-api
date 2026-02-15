package com.taskapi.taskmanager.controller;

import com.taskapi.taskmanager.dto.AuthResponse;
import com.taskapi.taskmanager.dto.LoginRequest;
import com.taskapi.taskmanager.dto.SignupRequest;
import com.taskapi.taskmanager.entity.User;
import com.taskapi.taskmanager.security.JwtUtil;
import com.taskapi.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = userService.registerUser(request);
        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                "User registered successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Generate JWT token
        String token = jwtUtil.generateToken(request.getUsername());
        User user = userService.findByUsername(request.getUsername());

        AuthResponse response = new AuthResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                "Login successful"
        );

        return ResponseEntity.ok(response);
    }
}