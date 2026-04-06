package com.micromarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorDTO {
    private Long id;

    @NotBlank(message = "El NIT es obligatorio")
    private String nit;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
}
