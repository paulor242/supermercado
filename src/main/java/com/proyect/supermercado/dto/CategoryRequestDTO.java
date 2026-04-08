package com.proyect.supermercado.dto;
import lombok.*;

/**
 * DTO para solicitudes de creación/actualización de categorías
 */
@Data
public class CategoryRequestDTO {

    private String name;
    private String description;
    private Boolean active;

    
}
