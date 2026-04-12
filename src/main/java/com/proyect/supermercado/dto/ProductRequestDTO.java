package com.proyect.supermercado.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para solicitudes de creación/actualización de productos
 */
@Data
@Builder
public class ProductRequestDTO {
    private String name;
    private String description;
    private String barcode;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Boolean active;
}
