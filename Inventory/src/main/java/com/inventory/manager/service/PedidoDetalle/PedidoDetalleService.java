package com.inventory.manager.service.PedidoDetalle;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
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
        if (pedidoDetalle == null) {
            throw new IllegalArgumentException("La entidad pedidoDetalle no puede ser nula.");
        }

        try {
            return pedidoDetalleRepository.save(pedidoDetalle);
        } catch (Exception e) {
            throw new DataBaseException("Error al guarda el detalle del pedido", e);
        }
    }
}
