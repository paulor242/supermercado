package com.micromarket.repository;

import com.micromarket.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de proveedores.
 * JpaRepository ya nos da save, findAll, findById, delete, etc. gratis.
 * Solo agregamos la consulta personalizada para verificar NITs duplicados.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    // Spring genera el SQL automáticamente: SELECT COUNT(*) > 0 FROM proveedores WHERE nit = ?
    boolean existsByNit(String nit);
}