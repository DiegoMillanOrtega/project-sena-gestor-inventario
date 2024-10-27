package com.inventory.manager.model.modelRequest;

import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDetalle;
import lombok.Data;

@Data
public class PedidoRequest {
    private Pedido pedido;
    private PedidoDetalleRequest pedidoDetalleRequest;
}
