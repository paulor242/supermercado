package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de ventas.
 * JpaRepository ya nos da gratis: save, findById, findAll, delete, count, etc.
 * No necesitamos agregar métodos extra por ahora.
 */
// findById ya viene incluido en JpaRepository — no hace falta redefinirlo
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
