package com.xyphx.getwarranty.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String ACCESS_SECRET = System.getenv("ACCESS_TOKEN_SECRET");
    private final String REFRESH_SECRET = System.getenv("REFRESH_TOKEN_SECRET");

    private final SecretKey ACCESS_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_SECRET));
    private final SecretKey REFRESH_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_SECRET));

    private static final long ACCESS_EXPIRATION = 1000L * 60 * 15; // 15 minutes
    private static final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 365; // 1 year

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(ACCESS_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(REFRESH_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmailFromAccessToken(String token) {
        return extractEmail(token, ACCESS_KEY);
    }

    public String extractEmailFromRefreshToken(String token) {
        return extractEmail(token, REFRESH_KEY);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, ACCESS_KEY);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, REFRESH_KEY);
    }

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
