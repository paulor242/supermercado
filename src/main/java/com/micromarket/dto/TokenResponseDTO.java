package com.micromarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de respuesta del login.
 * Solo contiene el token JWT que el cliente debe guardar
 * y enviar en cada petición posterior como "Authorization: Bearer <token>".
 */
@Data
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
}
