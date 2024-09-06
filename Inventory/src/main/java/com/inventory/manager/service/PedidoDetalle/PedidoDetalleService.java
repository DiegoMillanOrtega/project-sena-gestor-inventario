package com.inventory.manager.service.PedidoDetalle;

import com.inventory.manager.model.PedidoDetalle;
import com.inventory.manager.repository.IPedidoDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PedidoDetalleService implements IPedidoDetalleService{

    @Autowired
    private IPedidoDetalleRepository pedidoDetalleRepository;
    @Override
    public PedidoDetalle savePedidoDetalle(PedidoDetalle pedidoDetalle) {
        PedidoDetalle savedPedidoDetalle = pedidoDetalleRepository.save(pedidoDetalle);
        for (PedidoDetalle detalle: pedidoDetalle.getPedido().getPedidoDetalles()) {
            detalle.setId(pedidoDetalle.getId());
            detalle.setProducto(detalle.getProducto());
            pedidoDetalleRepository.save(detalle);
        }
        return savedPedidoDetalle;
    }
}
