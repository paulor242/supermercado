package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Entidad que representa el detalle de una venta — es decir, cada línea de producto.
 * Una venta (Sales) puede tener muchos detalles: uno por cada producto comprado.
 * La tabla en BD se llama "detailSale".
 */
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table (name ="detailSale")
public class DetailSale {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;
    @Column (name="amount")
    private  Integer amount;
    @Column(name="unitPrice")
    private BigDecimal unitPrice;
    @Column(name ="subTotal")
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name ="idProduct")
    private Product idProduct;

    @ManyToOne
    @JoinColumn(name ="idSale")
    private Sales sales;

}
