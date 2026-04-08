package com.micromarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO usado tanto para recibir datos al crear un proveedor
 * como para devolver la información del proveedor en las respuestas.
 * El id es null cuando se crea (aún no existe en BD) y viene lleno en las respuestas.
 */
@Data
public class ProveedorDTO {
    private Long id; // null al crear, lleno al consultar

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
}
