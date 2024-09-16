package com.inventory.manager.controller.Pedido.DetallePedido;


import com.inventory.manager.model.PedidoDetalle;
import com.inventory.manager.model.modelRequest.PedidoDetalleRequest;
import com.inventory.manager.repository.IInventoryRepository;
import com.inventory.manager.repository.IPedidoRepository;
import com.inventory.manager.service.PedidoDetalle.PedidoDetalleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidoDetalle")
@CrossOrigin(value = "http://localhost:4200")
public class PedidoDetalleController {

    public static final Logger logger = LoggerFactory.getLogger(PedidoDetalle.class);

    @Autowired
    private PedidoDetalleService pedidoDetalleService;

    @Autowired
    private IInventoryRepository inventoryRepository;

    @Autowired
    private IPedidoRepository pedidoRepository;

    @PostMapping("/savePedidoDetalle")
    public ResponseEntity<List<PedidoDetalle>> savePedidoDetalle(@RequestBody PedidoDetalleRequest request) {

        List<PedidoDetalle> detallesGuardados = new ArrayList<>();

        for (int i = 0; i < request.getProductos().size(); i++) {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(request.getPedido());
            detalle.setProducto(request.getProductos().get(i));
            detalle.setCantidades(request.getProductos().get(i).getStock());

            PedidoDetalle savedDetalle = pedidoDetalleService.savePedidoDetalle(detalle);
            detallesGuardados.add(savedDetalle);
        }
        // Retornar todos los detalles guardados al final del ciclo
        return new ResponseEntity<>(detallesGuardados, HttpStatus.CREATED);
    }

}
