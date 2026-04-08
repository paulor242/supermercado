package com.micromarket.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para todo lo relacionado con JWT: crear tokens, leerlos y validarlos.
 * Esta clase es el corazón de la autenticación sin sesiones del sistema.
 */
@Component
public class JwtUtil {

    // Clave secreta usada para firmar los tokens — si alguien la conoce, puede falsificar tokens
    private final SecretKey key = Keys.hmacShaKeyFor(
        "micromarket-secret-key-2024-abcd".getBytes(StandardCharsets.UTF_8)
    );

    // 24 horas en milisegundos — después de este tiempo el token expira y hay que volver a hacer login
    private final long EXPIRATION_MS = 86400000;

    /**
     * Genera un token JWT para el usuario que acaba de iniciar sesión.
     * El token lleva el nombre de usuario y la fecha de expiración firmados.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)                                                        // quién es el dueño del token
                .issuedAt(new Date())                                                     // cuándo se creó
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))        // cuándo vence
                .signWith(key)                                                            // firma con la clave secreta
                .compact();
    }

    /**
     * Saca el nombre de usuario que está guardado dentro del token.
     * Se usa para saber quién está haciendo la petición.
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Verifica que el token no esté vencido ni manipulado.
     * Si alguien alteró el token o ya expiró, devuelve false.
     */
    public boolean isTokenValid(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido, corrupto o con firma incorrecta
            return false;
        }
    }

    /**
     * Parsea el token y extrae toda la información (claims) que contiene.
     * Método privado porque solo lo usamos internamente en esta clase.
     */
    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
