package com.inventory.manager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigo;
    @Column(nullable = false, unique = true)
    private String category;
    @Column(length = 200)
    private String descrip;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    @CreationTimestamp
    private LocalDateTime fechaModificacion;

}
