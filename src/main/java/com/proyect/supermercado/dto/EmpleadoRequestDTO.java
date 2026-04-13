package com.proyect.supermercado.dto;
import com.proyect.supermercado.enume.Cargo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoRequestDTO {
    private String cedula;
    private String nombre;
    private Cargo cargo;
    private LocalDate fecha;
    private LocalDate fechaIngreso;
    private Double salario;
}
