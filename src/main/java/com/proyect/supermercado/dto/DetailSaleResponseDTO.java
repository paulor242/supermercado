package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de respuesta para un detalle de venta.
 * Incluye el objeto completo de la venta padre (SaleResponseDTO) embebido,
 * así el cliente recibe toda la información en una sola respuesta sin hacer otra petición.
 */
@Data
public class DetailSaleResponseDTO {

    private Long id;
    private Long idProduct;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private SaleResponseDTO sales; // la venta completa embebida — no solo el ID
}
