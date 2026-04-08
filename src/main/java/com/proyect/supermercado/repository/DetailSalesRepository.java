package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.DetailSale;
import org.springframework.data.jpa.repository.JpaRepository;

// findById ya viene incluido en JpaRepository — no hace falta redefinirlo
public interface DetailSalesRepository extends JpaRepository <DetailSale, Long> {
}
