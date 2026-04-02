package com.proyect.supermercado.repository;

import com.proyect.supermercado.entity.Restock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestockRepository extends JpaRepository<Restock, Long> {
}