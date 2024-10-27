package com.inventory.manager.service.Pedido;

import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDTO;

import java.util.List;

public interface IPedidoService {
    public List<PedidoDTO> findAllPedido();

    public Pedido savePedido(Pedido pedido);

}
