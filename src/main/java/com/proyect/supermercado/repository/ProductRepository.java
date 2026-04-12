package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca un producto por código de barras
     * Regla de Negocio 2: Validación de unicidad
     */
    Optional<Product> findByBarcode(String barcode);

    /**
     * Verifica si existe un producto por código de barras
     */
    boolean existsByBarcode(String barcode);

    /**
     * Busca productos por categoría ordenados por nombre
     */
    List<Product> findByCategoryIdOrderByNameAsc(Long categoryId);

    /**
     * Busca productos activos por categoría
     */
    List<Product> findByCategoryIdAndActiveTrueOrderByNameAsc(Long categoryId);

    /**
     * Busca todos los productos activos (soft delete) ordenados por nombre
     */
    List<Product> findByActiveTrueOrderByNameAsc();

    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    List<Product> findByNameContainingIgnoreCaseAndActiveTrueOrderByNameAsc(String name);

    /**
     * Verifica si existe un producto por código de barras excluyendo un ID específico
     * Para validación en actualizaciones
     */
    boolean existsByBarcodeAndIdNot(String barcode, Long id);
}