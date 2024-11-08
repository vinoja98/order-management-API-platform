package com.management.orders.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    // Generate a proper secret key - should be at least 256 bits for HS256
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString(
            "your_very_secure_and_very_long_secret_key_here_min_32_chars".getBytes(StandardCharsets.UTF_8)
    );

    private final SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
