package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RestockDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}