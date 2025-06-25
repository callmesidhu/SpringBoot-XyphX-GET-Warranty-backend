package com.xyphx.getwarranty.service;

import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User appUser = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with email: " + email));

                return new org.springframework.security.core.userdetails.User(
                                appUser.getEmail(),
                                appUser.getPassword(),
                                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
}
