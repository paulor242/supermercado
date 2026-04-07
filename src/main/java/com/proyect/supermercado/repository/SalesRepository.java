package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Sales;


import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findById(Long id);
}
