package com.micromarket.controller;

import com.micromarket.dto.LoginRequestDTO;
import com.micromarket.dto.TokenResponseDTO;
import com.micromarket.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación.
 * Solo tiene un endpoint: POST /api/auth/login
 * Esta ruta es pública — cualquiera puede intentar hacer login sin token.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Recibe usuario y contraseña, los valida y devuelve un token JWT.
     * Si las credenciales son incorrectas, Spring Security lanza 401 automáticamente.
     * @Valid asegura que los campos no vengan vacíos antes de llegar aquí.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
