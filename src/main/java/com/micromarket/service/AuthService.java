package com.micromarket.service;

import com.micromarket.dto.LoginRequestDTO;
import com.micromarket.dto.TokenResponseDTO;
import com.micromarket.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación.
 * Su única responsabilidad: verificar credenciales y entregar un token JWT.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Intenta autenticar al usuario con sus credenciales.
     * Si el usuario no existe o la contraseña es incorrecta, Spring lanza una excepción
     * y el flujo se corta aquí — nunca se genera el token.
     * Si todo está bien, genera y devuelve el JWT listo para usar.
     */
    public TokenResponseDTO login(LoginRequestDTO dto) {
        // authenticate() lanza BadCredentialsException si algo falla — Spring lo convierte en 401
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        // Si llegamos aquí, las credenciales son válidas — generamos el token
        return new TokenResponseDTO(jwtUtil.generateToken(dto.getUsername()));
    }
}
