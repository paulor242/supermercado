package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.DetailSale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de detalles de venta.
 * JpaRepository ya nos da gratis: save, findById, findAll, delete, count, etc.
 * No necesitamos agregar métodos extra por ahora.
 */
// findById ya viene incluido en JpaRepository — no hace falta redefinirlo
public interface DetailSalesRepository extends JpaRepository<DetailSale, Long> {
}
