package com.proyect.supermercado.dto;
import lombok.*;
import java.util.List;


/**
 * DTO para respuestas de categorías
 * Incluye resumen de productos vinculados (Regla de Negocio 3)
 */
@Data
@Builder
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean active;
    
    // Lista resumida de productos para evitar ciclos
    private List<ProductSummary> products;

    /**
     * Clase interna para resumen de producto
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductSummary {
        private Long id;
        private String name;
        private String barcode;
        private Boolean active;
    }
}
