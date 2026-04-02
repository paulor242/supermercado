package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}