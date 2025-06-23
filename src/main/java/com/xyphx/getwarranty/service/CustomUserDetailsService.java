package com.xyphx.getwarranty.service;

import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User appUser = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with email: " + email));

                UserBuilder builder = org.springframework.security.core.userdetails.User
                                .withUsername(appUser.getEmail());
                builder.password(appUser.getPassword());
                builder.authorities(new ArrayList<>()); // Add roles if needed

                return builder.build();
        }
}
