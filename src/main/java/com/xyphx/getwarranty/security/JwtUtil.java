package com.xyphx.getwarranty.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

        // Load .env secrets
        private final Dotenv dotenv = Dotenv.configure().load();

        // Base64 encoded secrets from .env
        private final String ACCESS_SECRET = dotenv.get("ACCESS_TOKEN_SECRET");
        private final String REFRESH_SECRET = dotenv.get("REFRESH_TOKEN_SECRET");

        // Decode secrets into HMAC keys
        private final SecretKey ACCESS_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_SECRET));
        private final SecretKey REFRESH_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_SECRET));

        // Token expiration times
        private static final long ACCESS_EXPIRATION = 1000L * 60 * 15; // 15 minutes
        private static final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 365; // 1 year

        /**
         * Generate an access token for the given email
         */
        public String generateAccessToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                                .signWith(ACCESS_KEY, SignatureAlgorithm.HS512)
                                .compact();
        }

        /**
         * Generate a refresh token for the given email
         */
        public String generateRefreshToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                                .signWith(REFRESH_KEY, SignatureAlgorithm.HS512)
                                .compact();
        }

        /**
         * Extract email (subject) from access token
         */
        public String extractEmailFromAccessToken(String token) {
                return extractEmail(token, ACCESS_KEY);
        }

        /**
         * Extract email (subject) from refresh token
         */
        public String extractEmailFromRefreshToken(String token) {
                return extractEmail(token, REFRESH_KEY);
        }

        /**
         * Validate access token
         */
        public boolean validateAccessToken(String token) {
                return validateToken(token, ACCESS_KEY);
        }

        /**
         * Validate refresh token
         */
        public boolean validateRefreshToken(String token) {
                return validateToken(token, REFRESH_KEY);
        }

        // --- Helper methods ---

        private String extractEmail(String token, SecretKey key) {
                try {
                        return Jwts.parserBuilder()
                                        .setSigningKey(key)
                                        .build()
                                        .parseClaimsJws(token)
                                        .getBody()
                                        .getSubject();
                } catch (JwtException e) {
                        System.out.println("Failed to extract email: " + e.getMessage());
                        return null;
                }
        }

        private boolean validateToken(String token, SecretKey key) {
                try {
                        Jwts.parserBuilder()
                                        .setSigningKey(key)
                                        .build()
                                        .parseClaimsJws(token);
                        return true;
                } catch (JwtException e) {
                        System.out.println("Invalid JWT token: " + e.getMessage());
                        return false;
                }
        }
}
