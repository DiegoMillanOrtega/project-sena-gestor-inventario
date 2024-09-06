package com.inventory.manager.controller.Pedido.DetallePedido;

import com.inventory.manager.controller.UsersController;
import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDetalle;
import com.inventory.manager.repository.IInventoryRepository;
import com.inventory.manager.repository.IPedidoRepository;
import com.inventory.manager.service.PedidoDetalle.PedidoDetalleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> savePedidoDetalle(@Validated @RequestBody PedidoDetalle pedidoDetalle, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            PedidoDetalle savedPedidoDetalle = this.pedidoDetalleService.savePedidoDetalle(pedidoDetalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPedidoDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el detalle del pedido");
        }
    }

}
