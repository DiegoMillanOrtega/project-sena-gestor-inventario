package com.inventory.manager.model;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {
    private Pedido pedido;
    private List<Inventory> productos;
    private List<Integer> cantidades;
}
