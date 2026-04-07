package com.proyect.supermercado.controller;
import com.proyect.supermercado.dto.EmpleadoRequestDTO;
import com.proyect.supermercado.dto.EmpleadoResponseDTO;
import com.proyect.supermercado.enume.Cargo;
import com.proyect.supermercado.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) 
        {this.service = service;}

    @PostMapping
    public EmpleadoResponseDTO crear(@Valid @RequestBody EmpleadoRequestDTO dto)
        {return service.crear(dto);}

    @GetMapping("/cargo")
    public List<EmpleadoResponseDTO> porCargo(@RequestParam Cargo cargo)
        {return service.listarPorCargo(cargo);}

    @GetMapping("/fechas")
    public List<EmpleadoResponseDTO> porFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) 
        {return service.listarPorRangoFechas(inicio, fin);}

    @GetMapping("/{id}")
    public EmpleadoResponseDTO obtener(@PathVariable Long id) 
        {return service.obtenerPorId(id);}}
