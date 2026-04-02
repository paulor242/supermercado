package com.proyect.supermercado.dto;

import lombok.Data;

@Data
public class RestockDetailRequestDTO {
    private Long productId;
    private Integer quantity;
}