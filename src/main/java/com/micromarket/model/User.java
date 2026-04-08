package com.micromarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un usuario del sistema.
 * Estos son los usuarios que pueden hacer login y obtener un token JWT.
 * La tabla en BD se llama "users".
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // no pueden existir dos usuarios con el mismo username
    private String username;

    @Column(nullable = false) // la contraseña se guarda encriptada con BCrypt, nunca en texto plano
    private String password;

    private String role; // ej: "ADMIN", "USER" — se usa para construir el ROLE_ en SecurityConfig
}
