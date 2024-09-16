package com.inventory.manager.service.Pedido;

import com.inventory.manager.model.Client;
import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;

import java.util.List;

public interface IPedidoService {
    public List<Pedido> findAllPedido();

    public Pedido savePedido(Pedido pedido);

}
