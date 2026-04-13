package com.proyect.supermercado.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String message;
    private  String jwt;
}
