package com.micromarket.service;

import com.micromarket.dto.ProveedorDTO;
import com.micromarket.model.Proveedor;
import com.micromarket.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio con la lógica de negocio de proveedores.
 * Aquí vive la regla más importante: no se pueden duplicar NITs.
 */
@Service
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra un nuevo proveedor en la base de datos.
     * Primero verifica que el NIT no esté duplicado — si ya existe, lanza excepción
     * que el GlobalExceptionHandler convierte en un 409 Conflict.
     */
    public ProveedorDTO crear(ProveedorDTO dto) {
        if (repository.existsByNit(dto.getNit())) {
            throw new IllegalArgumentException("El NIT ya está registrado en la base de datos");
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setNit(dto.getNit());
        proveedor.setNombre(dto.getNombre());
        repository.save(proveedor);

        return toDTO(proveedor);
    }

    /**
     * Trae todos los proveedores de la BD y los convierte a DTO.
     * Usamos stream + toList() para no exponer las entidades JPA directamente.
     */
    public List<ProveedorDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Convierte una entidad Proveedor a su DTO de respuesta.
     * Separamos esto en un método para no repetir el mapeo en cada lugar.
     */
    private ProveedorDTO toDTO(Proveedor p) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(p.getId());
        dto.setNit(p.getNit());
        dto.setNombre(p.getNombre());
        return dto;
    }
}
