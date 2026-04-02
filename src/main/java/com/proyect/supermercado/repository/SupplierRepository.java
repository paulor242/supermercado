package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}