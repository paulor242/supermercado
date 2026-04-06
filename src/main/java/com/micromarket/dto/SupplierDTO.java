package com.micromarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierDTO {
    private Long id;

    @NotBlank(message = "NIT is required")
    private String nit;

    @NotBlank(message = "Name is required")
    private String name;
}
