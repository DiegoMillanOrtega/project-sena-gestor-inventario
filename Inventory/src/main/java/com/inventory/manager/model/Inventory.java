package com.inventory.manager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    private Long price;
    private Integer stock;
}
