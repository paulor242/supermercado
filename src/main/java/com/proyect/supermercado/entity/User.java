package com.proyect.supermercado.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="username")
    private String userName;
    @Column(name ="password")
    private String password;
    @Column(name = "rol")
    private String rol;
}