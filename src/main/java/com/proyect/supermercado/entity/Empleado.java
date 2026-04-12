package com.proyect.supermercado.entity;
import com.proyect.supermercado.enume.Cargo;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cedula;
    @Column (name ="nombre")
    private  String nombre;
    @Column(name ="feche")
    private LocalDate fecha;
    @Column (name ="fecha_ingreso")
    private LocalDate fechaIngreso;
    @Column (name ="salario")
    private Double salario;

    @Enumerated(EnumType.STRING)
    @Column(name ="cargo")
    private Cargo cargo;
}
