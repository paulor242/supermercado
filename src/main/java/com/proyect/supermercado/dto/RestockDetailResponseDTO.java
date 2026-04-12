package com.proyect.supermercado.dto;

import lombok.Data;

@Data
public class RestockDetailResponseDTO {
    private String productName;
    private Integer quantity;
}