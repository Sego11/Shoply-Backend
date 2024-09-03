package com.Shoply_Backend.controllers;

import com.Shoply_Backend.domain.dto.auth.AuthRequest;
import com.Shoply_Backend.domain.dto.auth.AuthResponse;
import com.Shoply_Backend.domain.dto.auth.SignUpRequest;
import com.Shoply_Backend.services.impl.AuthService;
import com.Shoply_Backend.services.impl.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
        authService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.signIn(authRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> getPayloadFromToken(@RequestHeader("Authorization") String token){
        if (token.startsWith("Bearer "))
            token = token.substring(7);

        if(authService.validateToken(token)){
            Claims claims = jwtService.extractAllClaims(token);
            return ResponseEntity.ok(claims);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired Token");
        }

    }
}
