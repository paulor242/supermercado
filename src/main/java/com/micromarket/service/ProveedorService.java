package com.micromarket.service;

import com.micromarket.dto.ProveedorDTO;
import com.micromarket.model.Proveedor;
import com.micromarket.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

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

    public List<ProveedorDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private ProveedorDTO toDTO(Proveedor p) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(p.getId());
        dto.setNit(p.getNit());
        dto.setNombre(p.getNombre());
        return dto;
    }
}
