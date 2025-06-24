package com.xyphx.getwarranty.controller;

import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

        @Autowired
        private UserRepository userRepository;

        // GET /api/user/details
        @GetMapping("/details")
        public ResponseEntity<?> getUserDetails(Authentication authentication) {
                String userEmail = authentication.getName();

                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (optionalUser.isEmpty()) {
                        return ResponseEntity.badRequest().body("User not found");
                }

                User user = optionalUser.get();

                // Avoid sending password in response
                user.setPassword(null);

                return ResponseEntity.ok(user);
        }

        // PATCH /api/user/profile
        @PatchMapping("/profile")
        public ResponseEntity<?> addProfile(@RequestBody com.xyphx.getwarranty.model.Profile profile,
                        Authentication authentication) {
                String userEmail = authentication.getName();

                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (optionalUser.isEmpty()) {
                        return ResponseEntity.badRequest().body("User not found");
                }

                User user = optionalUser.get();
                user.setProfile(profile);
                userRepository.save(user);

                return ResponseEntity.ok("Profile added successfully");
        }
}
