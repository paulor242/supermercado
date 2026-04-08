package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table (name ="detailSale")
public class DetailSale {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;
    @Column (name="amount")
    private  Integer amount;
    @Column(name="unitPrice")
    private BigDecimal unitPrice;
    @Column(name ="subTotal")
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name ="idProduct")
    private Product idProduct;

<<<<<<< HEAD
=======
    // TODO: convertir a relación @ManyToOne con la entidad Producto cuando esté disponible
    @Column(name = "idProduct")
    private Long idProduct; // ID del producto vendido

    // Relación con la venta padre — muchos detalles pertenecen a una sola venta
>>>>>>> 523ad6b7b7c3b2ffb17c5fd8f76ed6f4e69f54cb
    @ManyToOne
    @JoinColumn(name ="idSale")
    private Sales sales;

}
