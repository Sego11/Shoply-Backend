package com.Shoply_Backend.services;
import com.Shoply_Backend.domain.dto.auth.AuthRequest;
import com.Shoply_Backend.domain.dto.auth.SignUpRequest;
import com.Shoply_Backend.domain.entities.User;
import com.Shoply_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public void signup(SignUpRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }


    public String signIn(AuthRequest request){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getEmail(),
//                request.getPassword()
//        )
//        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return jwtService.generateToken(user);
        else
            throw new IllegalArgumentException("Invalid credentials");
    }

}
