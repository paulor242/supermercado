package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Sales;


import org.springframework.data.jpa.repository.JpaRepository;


// findById ya viene incluido en JpaRepository — no hace falta redefinirlo
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
