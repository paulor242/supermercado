package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.DetailSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailSalesRepository extends JpaRepository <DetailSale, Long> {
    Optional<DetailSale> findById(Long id);
}
