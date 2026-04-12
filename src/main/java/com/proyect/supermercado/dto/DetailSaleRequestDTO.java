package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para crear un detalle de venta.
 * El cliente manda el ID del producto, la cantidad, el precio unitario
 * y el ID de la venta a la que pertenece este detalle.
 */
@Data
public class DetailSaleRequestDTO {

    private Long idProduct;       // qué producto se está vendiendo
    private Integer amount;       // cuántas unidades
    private BigDecimal unitPrice; // precio por unidad en este momento
    private BigDecimal subTotal;  // amount * unitPrice — el cliente lo calcula y lo manda
    private Long sales;           // ID de la venta padre a la que pertenece este detalle
}
