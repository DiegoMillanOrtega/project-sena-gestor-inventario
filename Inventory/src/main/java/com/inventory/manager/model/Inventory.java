package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PedidoDetalle> pedidoDetalles;
}
