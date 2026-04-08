package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una venta en el supermercado.
 * Cada registro aquí es una transacción completa con su fecha, montos e impuestos.
 * La tabla en BD se llama "sales".
 */
@Entity
@Data
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dateSale")
    private LocalDate dateSale; // fecha en que se realizó la venta

    @Column(name = "subTotal")
    private BigDecimal SubTotal; // valor antes de impuestos

    @Column(name = "vat")
    private BigDecimal vat; // IVA aplicado a la venta

    @Column(name = "total")
    private BigDecimal total; // subTotal + vat = lo que paga el cliente

    @Column(name = "state")
    private String state; // estado de la venta: ej. "ACTIVA", "ANULADA", "PAGADA"

    // TODO: convertir a relación @ManyToOne con la entidad Empleado cuando esté disponible
    @Column(name = "idempleado")
    private Long idEmpleado; // ID del empleado que registró la venta
}
