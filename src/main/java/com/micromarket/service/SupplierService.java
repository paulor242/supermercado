package com.micromarket.service;

import com.micromarket.dto.SupplierDTO;
import com.micromarket.model.Supplier;
import com.micromarket.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public SupplierDTO create(SupplierDTO dto) {
        if (repository.existsByNit(dto.getNit())) {
            throw new IllegalArgumentException("NIT already registered");
        }
        Supplier supplier = new Supplier();
        supplier.setNit(dto.getNit());
        supplier.setName(dto.getName());
        repository.save(supplier);
        return toDTO(supplier);
    }

    public List<SupplierDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private SupplierDTO toDTO(Supplier s) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(s.getId());
        dto.setNit(s.getNit());
        dto.setName(s.getName());
        return dto;
    }
}
