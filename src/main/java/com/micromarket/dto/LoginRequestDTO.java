package com.micromarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para recibir las credenciales del usuario en el endpoint de login.
 * @NotBlank evita que alguien mande campos vacíos o solo espacios.
 */
@Data
public class LoginRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
