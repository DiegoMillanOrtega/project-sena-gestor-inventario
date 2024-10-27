package com.inventory.manager.service.Pedido;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.*;
import com.inventory.manager.repository.*;
import com.inventory.manager.service.Inventory.InventoryService;
import com.inventory.manager.service.PedidoDetalle.PedidoDetalleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService implements IPedidoService{

    public static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private PedidoDetalleService detalleService;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public List<PedidoDTO> findAllPedido() {
        return this.pedidoRepository.findAllPedidoDetails();
    }

    @Override
    public Pedido savePedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("La entidad Pedido no puede ser nula.");
        }
        try {
            for (PedidoDetalle detalle: pedido.getPedidoDetalles()) {

                detalle.setPedido(pedido);
                detalle.setCantidades(detalle.getProducto().getStock());

                Inventory producto = detalle.getProducto();
                producto.setStock(producto.getStock() - detalle.getCantidades());

                inventoryService.saveProduct(producto);
            }
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new DataBaseException("Error al guarda el pedido", e);
        }
    }
}
