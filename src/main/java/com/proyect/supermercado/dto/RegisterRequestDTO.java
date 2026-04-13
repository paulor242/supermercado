package com.proyect.supermercado.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String password;
    private String userName;
    private String rol;
}
