package com.micromarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Entidad que representa un proveedor del supermercado.
 * Un proveedor puede suministrar múltiples productos (relación muchos a muchos).
 * La tabla en BD se llama "proveedores".
 */
@Entity
@Table(name = "proveedores")
@Getter
@Setter
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BD genera el ID automáticamente (autoincrement)
    private Long id;

    @Column(nullable = false, unique = true) // el NIT es obligatorio y no puede repetirse en toda la tabla
    private String nit;

    private String nombre;

    // La relación está mapeada desde el lado de Producto (mappedBy)
    // esto evita que JPA cree una tabla intermedia duplicada
    @ManyToMany(mappedBy = "proveedores")
    private List<Producto> productos;
}
