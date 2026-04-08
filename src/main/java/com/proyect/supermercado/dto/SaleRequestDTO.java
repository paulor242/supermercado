package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para recibir los datos al crear o actualizar una venta.
 * El cliente manda este objeto en el body del POST y PUT.
 * No incluye el id porque ese lo genera la BD automáticamente.
 */
@Data
public class SaleRequestDTO {

    private LocalDate dateSale;   // fecha de la venta — el cliente la manda explícitamente
    private BigDecimal subTotal;  // valor sin IVA
    private BigDecimal vat;       // monto del impuesto
    private BigDecimal total;     // total final que paga el cliente
    private String state;         // estado inicial de la venta al crearla
    private Long idEmpleado;      // quién registró la venta
}
