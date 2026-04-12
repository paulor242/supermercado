package com.proyect.supermercado.Mapper;

import com.proyect.supermercado.dto.EmpleadoRequestDTO;
import com.proyect.supermercado.dto.EmpleadoResponseDTO;
import com.proyect.supermercado.entity.Empleado;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequestDTO dto)
    {Empleado e = new Empleado();
     e.setCedula(dto.getCedula());
     e.setNombre(dto.getNombre());
     e.setCargo(dto.getCargo());
     e.setFecha(dto.getFecha());
     e.setFechaIngreso(dto.getFechaIngreso());
     e.setSalario(dto.getSalario());
     return e;}

    public static EmpleadoResponseDTO toDTO(Empleado e)
    {EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
     dto.setId(e.getId());
     dto.setCedula(e.getCedula());
     dto.setNombre(e.getNombre());
     dto.setCargo(e.getCargo());
     dto.setFecha(e.getFecha());
     dto.setFechaIngreso(e.getFechaIngreso());
     dto.setSalario(e.getSalario());
     return dto;}}
