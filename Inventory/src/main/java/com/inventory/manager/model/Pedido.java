package com.inventory.manager.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String product;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String client;
    @Column(nullable = false)
    private boolean confirmedDelivery;
}
