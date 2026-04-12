package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de respuesta para las operaciones sobre ventas.
 * Es lo que el servidor devuelve al cliente después de crear, consultar o actualizar.
 * Igual que el request pero incluye el id que asignó la BD.
 */
@Data
public class SaleResponseDTO {

    private Long id;              // ID generado por la BD — no existe en el request
    private LocalDate dateSale;
    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;
    private String state;
    private Long idEmpleado;
}
