package com.proyect.supermercado.dto;
import com.proyect.supermercado.enume.Cargo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoResponseDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private Cargo cargo;
    private LocalDate fecha;
    private LocalDate fechaIngreso;
    private Double salario;
}
