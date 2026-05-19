package com.example.ics.Auth;

import com.example.ics.Auth.dto.AuthResponse;
import com.example.ics.Auth.dto.LoginRequest;
import com.example.ics.Auth.dto.RegisterRequest;
import com.example.ics.Repository.UserRepository;
import com.example.ics.Security.JwtUtil;
import com.example.ics.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("VIEWER")
                .created_at(LocalDateTime.now())
                .build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user doesnt found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("password incorrect");
        }
            String token = jwtUtil.generateToken(request.getEmail());
            return new AuthResponse(token);

    }

}
