package com.xyphx.getwarranty.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
                String path = request.getRequestURI();

                // Fix: Match full prefix, not Ant-style
                return path.startsWith("/api/auth") ||
                                path.startsWith("/swagger") ||
                                path.startsWith("/v3/api-docs");
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {

                final String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        filterChain.doFilter(request, response);
                        return;
                }

                final String token = authHeader.substring(7);

                try {
                        if (!jwtUtil.validateAccessToken(token)) {
                                System.out.println("JWT is not valid.");
                                filterChain.doFilter(request, response);
                                return;
                        }

                        final String email = jwtUtil.extractEmailFromAccessToken(token);

                        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                userDetails, null, userDetails.getAuthorities());

                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                        }

                } catch (Exception e) {
                        System.out.println("Error processing JWT: " + e.getMessage());
                }

                filterChain.doFilter(request, response);
        }
}
