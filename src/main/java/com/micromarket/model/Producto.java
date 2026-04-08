package com.micromarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Entidad que representa un producto del supermercado.
 * Un producto puede tener varios proveedores y un proveedor puede abastecer varios productos.
 * La tabla intermedia "producto_proveedor" guarda esa relación en BD.
 */
@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // el nombre del producto es obligatorio
    private String nombre;

    private Double precio;

    // Este lado es el dueño de la relación — JPA crea la tabla intermedia aquí
    @ManyToMany
    @JoinTable(
        name = "producto_proveedor",                              // nombre de la tabla intermedia en BD
        joinColumns = @JoinColumn(name = "producto_id"),          // columna que apunta a este producto
        inverseJoinColumns = @JoinColumn(name = "proveedor_id")   // columna que apunta al proveedor
    )
    private List<Proveedor> proveedores;
}
