package com.inventory.manager.service.Pedido;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDetalle;
import com.inventory.manager.model.modelRequest.PedidoDetalleRequest;
import com.inventory.manager.repository.IInventoryRepository;
import com.inventory.manager.service.PedidoDetalle.PedidoDetalleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PedidoTransaccionalService {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoDetalleService pedidoDetalleService;
    @Autowired
    private IInventoryRepository inventoryRepository;

//    @Transactional
//    public Pedido guardarPedidoYDetalles(PedidoDetalleRequest pedidoDetalleRequest) {
//
//        Pedido savedPedido = pedidoService.savePedido(pedidoDetalleRequest.getPedido());
//
//        for (int i = 0; i < pedidoDetalleRequest.getProductos().size(); i++) {
//            PedidoDetalle detalle = new PedidoDetalle();
//            detalle.setPedido(savedPedido);
//            detalle.setProducto(pedidoDetalleRequest.getProductos().get(i));
//            detalle.setCantidades(pedidoDetalleRequest.getProductos().get(i).getStock());
//
//            PedidoDetalle savedDetalle = pedidoDetalleService.savePedidoDetalle(detalle);
//        }
//        return savedPedido;
//    }
}
