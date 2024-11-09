package com.Shoply_Backend.services.impl;
import com.Shoply_Backend.domain.dto.auth.AuthRequest;
import com.Shoply_Backend.domain.dto.auth.AuthResponse;
import com.Shoply_Backend.domain.dto.auth.SignUpRequest;
import com.Shoply_Backend.domain.entities.User;
import com.Shoply_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    public void signup(SignUpRequest request){
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }


    public AuthResponse signIn(AuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        log.debug("user from db {} ", user);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id",user.getId());
        extraClaims.put("firstname",user.getFirstname());
        extraClaims.put("lastname",user.getLastname());
        var jwtToken = jwtService.generateToken(extraClaims,user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean validateToken(String token) {
        String username = jwtService.extractUsername(token);
        var user = userDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(token, user);
    }

}
