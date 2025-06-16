package com.xyphx.getwarranty.service;

import com.xyphx.getwarranty.dto.SignupRequest;
import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        @Autowired
        private UserRepository userRepository;

        private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        public String signup(SignupRequest request) {
                // Check if email already exists
                if (userRepository.existsByEmail(request.getEmail())) {
                        return "User already exists";
                }

                // Encrypt password before saving
                String encryptedPassword = encoder.encode(request.getPassword());

                // Create user object
                User user = new User(request.getName(), request.getEmail(), encryptedPassword);

                // Save to database
                userRepository.save(user);
                return "User registered successfully";
        }
}
