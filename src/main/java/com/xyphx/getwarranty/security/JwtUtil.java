package com.xyphx.getwarranty.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
        private final String ACCESS_SECRET = "access_secret"; // store in .env
        private final String REFRESH_SECRET = "refresh_secret";

        private final long ACCESS_EXPIRATION = 1000 * 60 * 15; // 15 mins
        private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 365; // 1 year

        public String generateAccessToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                                .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET)
                                .compact();
        }

        public String generateRefreshToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET)
                                .compact();
        }

        public String extractEmailFromAccessToken(String token) {
                return Jwts.parser().setSigningKey(ACCESS_SECRET).parseClaimsJws(token).getBody().getSubject();
        }

        public String extractEmailFromRefreshToken(String token) {
                return Jwts.parser().setSigningKey(REFRESH_SECRET).parseClaimsJws(token).getBody().getSubject();
        }

        public boolean validateAccessToken(String token) {
                try {
                        Jwts.parser().setSigningKey(ACCESS_SECRET).parseClaimsJws(token);
                        return true;
                } catch (JwtException e) {
                        return false;
                }
        }

        public boolean validateRefreshToken(String token) {
                try {
                        Jwts.parser().setSigningKey(REFRESH_SECRET).parseClaimsJws(token);
                        return true;
                } catch (JwtException e) {
                        return false;
                }
        }
}
