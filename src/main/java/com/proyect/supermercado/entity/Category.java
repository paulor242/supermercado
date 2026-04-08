package com.proyect.supermercado.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Entidad que representa una categoría de productos
 */
@Entity
@Table(name = "category")
@Data
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Product> products = new ArrayList<>();

      /**
     * Método helper para verificar si está activa
     * Evita NullPointerException si active es null
     */
    public boolean isActive() {
        return Boolean.TRUE.equals(this.active);
    }

    /**
     * Método helper para activar/desactivar
     */
    public void setActiveState(boolean active) {
        this.active = active;
    }

}
