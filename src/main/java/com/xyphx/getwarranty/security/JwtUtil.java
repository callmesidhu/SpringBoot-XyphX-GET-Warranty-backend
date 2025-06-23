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
        private final Dotenv dotenv = Dotenv.configure().load();

        // Raw base64 secrets from .env
        private final String ACCESS_SECRET = dotenv.get("ACCESS_TOKEN_SECRET");
        private final String REFRESH_SECRET = dotenv.get("REFRESH_TOKEN_SECRET");

        // Decode & key-wrap once on startup
        private final SecretKey ACCESS_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_SECRET));
        private final SecretKey REFRESH_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_SECRET));

        private final long ACCESS_EXPIRATION = 1000 * 60 * 15 * 24 * 365;
        private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 365; // 1 year

        public String generateAccessToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                                .signWith(ACCESS_KEY, SignatureAlgorithm.HS512) // ← new method
                                .compact();
        }

        public String generateRefreshToken(String email) {
                return Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                                .signWith(REFRESH_KEY, SignatureAlgorithm.HS512) // ← new method
                                .compact();
        }

        public String extractEmailFromAccessToken(String token) {
                return Jwts.parserBuilder()
                                .setSigningKey(ACCESS_KEY)
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .getSubject();
        }

        public String extractEmailFromRefreshToken(String token) {
                return Jwts.parserBuilder()
                                .setSigningKey(REFRESH_KEY)
                                .build()
                                .parseClaimsJws(token)
                                .getBody()
                                .getSubject();
        }

        public boolean validateAccessToken(String token) {
                try {
                        Jwts.parserBuilder()
                                        .setSigningKey(ACCESS_KEY)
                                        .build()
                                        .parseClaimsJws(token);
                        return true;
                } catch (JwtException e) {
                        return false;
                }
        }

        public boolean validateRefreshToken(String token) {
                try {
                        Jwts.parserBuilder()
                                        .setSigningKey(REFRESH_KEY)
                                        .build()
                                        .parseClaimsJws(token);
                        return true;
                } catch (JwtException e) {
                        return false;
                }
        }
}
