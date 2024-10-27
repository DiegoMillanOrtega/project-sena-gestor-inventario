package com.inventory.manager.controller.Pedido;

import com.inventory.manager.controller.UsersController;
import com.inventory.manager.model.*;
import com.inventory.manager.model.modelRequest.PedidoDetalleRequest;
import com.inventory.manager.model.modelRequest.PedidoRequest;
import com.inventory.manager.repository.IClienteRepository;
import com.inventory.manager.service.Pedido.PedidoService;
import com.inventory.manager.service.Pedido.PedidoTransaccionalService;
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

    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private PedidoTransaccionalService pedidoTransaccionalService;

        @GetMapping("/getPedidos")
    public List<PedidoDTO> pedidoList() {
        logger.info("Return pedidoList");
        return this.pedidoService.findAllPedido();
    }

    @PostMapping("/savePedido")
    public ResponseEntity<Pedido> savePedido(@RequestBody Pedido pedido) {
        try {
            Pedido savedPedido = pedidoService.savePedido(pedido);
            return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error al guardar el pedido: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

}
