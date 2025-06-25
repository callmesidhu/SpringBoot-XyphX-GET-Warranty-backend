package com.xyphx.getwarranty.service;

import com.xyphx.getwarranty.dto.*;
import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;
import com.xyphx.getwarranty.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private BCryptPasswordEncoder encoder;
        @Autowired
        private JwtUtil jwtUtil;

        public AuthResponse signup(SignupRequest req) {
                if (userRepository.findByEmail(req.getEmail()).isPresent()) {
                        System.out.println("Signup failed: user already exists with " + req.getEmail());
                        throw new RuntimeException("Email already exists!");
                }

                String hashed = encoder.encode(req.getPassword());
                User user = new User(req.getName(), req.getEmail(), hashed);
                userRepository.save(user);

                return new AuthResponse(
                                jwtUtil.generateAccessToken(user.getEmail()),
                                jwtUtil.generateRefreshToken(user.getEmail()));
        }

        public AuthResponse login(LoginRequest req) {
                User user = userRepository.findByEmail(req.getEmail())
                                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

                if (!encoder.matches(req.getPassword(), user.getPassword())) {
                        throw new RuntimeException("Invalid email or password");
                }

                return new AuthResponse(
                                jwtUtil.generateAccessToken(user.getEmail()),
                                jwtUtil.generateRefreshToken(user.getEmail()));
        }

        public AuthResponse refreshAccessToken(String refreshToken) {
                if (!jwtUtil.validateRefreshToken(refreshToken)) {
                        throw new RuntimeException("Invalid refresh token");
                }

                String email = jwtUtil.extractEmailFromRefreshToken(refreshToken);

                return new AuthResponse(
                                jwtUtil.generateAccessToken(email),
                                jwtUtil.generateRefreshToken(email));
        }
}
