package com.micromarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nit;

    private String name;

    @ManyToMany(mappedBy = "suppliers")
    private List<Product> products;
}
