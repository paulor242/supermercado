package com.proyect.supermercado.service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Value("${security.jwt.token-expiration}")
    Long tokenExpiration;

    private SecretKey getSignKey() {
        byte[] keyBites = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }

    public String generateToken(Long Id, String username, String rol) {
        return Jwts.builder()
                .claims(Map.of("userId", Id))
                .subject(username)
                .subject(rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }


    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    public String extractRol(String tocken){return extractClaims(tocken, Claims ::getSubject);}
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public Long extractUserId(String token) {
        return extractClaims(token, claims -> claims.get("userId", Long.class));
    }


    public String refreshToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token is expired: " + e.getMessage());
        } catch (JwtException e) {
            throw new RuntimeException("Token is invalid: " + e.getMessage());
        }

        return generateToken(claims.get("userId", Long.class), claims.getSubject(), claims.getSubject());
    }
}
