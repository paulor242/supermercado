package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Entidad que representa el detalle de una venta — es decir, cada línea de producto.
 * Una venta (Sales) puede tener muchos detalles: uno por cada producto comprado.
 * La tabla en BD se llama "detailSale".
 */
@Entity
@Data
@Table(name = "detailSale")
public class DetailSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Integer amount; // cantidad de unidades del producto en esta línea

    @Column(name = "unitPrice")
    private BigDecimal unitPrice; // precio por unidad al momento de la venta

    @Column(name = "subTotal")
    private BigDecimal subTotal; // amount * unitPrice = subtotal de esta línea

    // TODO: convertir a relación @ManyToOne con la entidad Producto cuando esté disponible
    @Column(name = "idProduct")
    private Long idProduct; // ID del producto vendido

    // Relación con la venta padre — muchos detalles pertenecen a una sola venta
    @ManyToOne
    @JoinColumn(name = "idSale")
    private Sales sales;
}
