package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SaleResponseDTO {
    private Long id;
    private LocalDate dateSale;
    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;
    private String state;
    private EmpleadoResponseDTO empleado;
}
