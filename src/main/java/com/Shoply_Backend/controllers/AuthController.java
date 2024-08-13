package com.Shoply_Backend.controllers;

import com.Shoply_Backend.domain.dto.auth.AuthRequest;
import com.Shoply_Backend.domain.dto.auth.AuthResponse;
import com.Shoply_Backend.domain.dto.auth.SignUpRequest;
import com.Shoply_Backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
        authService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest){
        String token = authService.signIn(authRequest);
        return ResponseEntity.ok(new AuthResponse((token)));
    }
}
