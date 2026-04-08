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

    @ManyToOne()
    @JoinColumn(name=" idEmpleado")
    private Empleado idempleado;

    public void setIdempleado(Long id) {
    }
}
