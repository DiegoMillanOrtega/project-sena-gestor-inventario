package com.inventory.manager.controller.Pedido;

import com.inventory.manager.controller.UsersController;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.service.Pedido.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(value = "http://localhost:4200")
public class PedidoController {

    public static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/getPedidos")
    public List<Pedido> pedidoList() {
        logger.info("Return pedidoList");
        return this.pedidoService.findAllPedido();
    }

    @PostMapping("/savePedido")
    public ResponseEntity<String> savePedido(@RequestBody Pedido pedido) {
        try {
            this.pedidoService.savePedido(pedido);
            logger.info("Saved pedido");
            return ResponseEntity.ok("Saved pedido successfully");
        } catch (Exception e) {
            logger.info("Error saving pedido",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving pedido");
        }
    }

    @PostMapping("/confirmedDelivery")
    public ResponseEntity<String> confirmedDelivery(@RequestBody Pedido pedido) {
        try {
            this.pedidoService.confirmedDelivery(pedido);
            logger.info("Delivery confirmed");
            return ResponseEntity.ok("Delivery confirmed");
        } catch (Exception e) {
            logger.info("Error confirming delivery",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error confirming delivery");
        }
    }
}
