package com.inventory.manager.model.modelRequest;


import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import lombok.Data;

import java.util.List;

@Data
public class PedidoDetalleRequest {
    private Pedido pedido;
    private List<Inventory> productos;
}
