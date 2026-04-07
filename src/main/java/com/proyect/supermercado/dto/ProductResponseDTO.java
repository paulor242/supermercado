package com.proyect.supermercado.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String barcode;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
    private CategorySummary category;

    /**
     * Clase interna para resumen de categoría
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategorySummary {
        private Long id;
        private String name;
    }
}
