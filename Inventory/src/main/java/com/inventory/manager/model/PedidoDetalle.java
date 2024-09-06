package com.inventory.manager.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventory", nullable = false)
    private Inventory producto;

    private Integer cantidades;
}
