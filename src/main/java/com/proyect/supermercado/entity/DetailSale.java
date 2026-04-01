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

    // colocar la relacion
    @Column (name ="idProduct")
    private Long idProduct;

    @ManyToOne
    @JoinColumn(name ="idSale")
    private Sales sales;

}
