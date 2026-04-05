package com.proyect.supermercado.dto;
import com.proyect.supermercado.enume.Cargo;
import jakarta.validation.constraints.*;


public class EmpleadoRequestDTO {
    @NotBlank
    private String cedula;

    @NotBlank
    private String nombre;

    @NotNull
    private Cargo cargo;}
