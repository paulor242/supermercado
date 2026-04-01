package com.proyect.supermercado.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailSaleRequestDTO {
    private Long idProduct;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private Long sales;
}
