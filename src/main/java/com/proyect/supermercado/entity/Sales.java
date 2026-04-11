package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name ="sales")
public class Sales {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name ="id")
    private Long id;
    @Column (name="dateSale")
    private LocalDate dateSale;
    @Column (name ="subTotal")
    private BigDecimal SubTotal;
    @Column (name ="vat")
    private BigDecimal vat;
    @Column (name ="total")
    private BigDecimal total;
    @Column(name ="state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado idempleado;

    public void setIdempleado(Long id) {
    }
}
