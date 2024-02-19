package com.inventory.manager.service.Pedido;

import com.inventory.manager.model.Pedido;

import java.util.List;

public interface IPedidoService {
    public List<Pedido> findAllPedido();
    public void savePedido(Pedido pedido);
    public void confirmedDelivery(Pedido pedido);
}
