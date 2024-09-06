package com.inventory.manager.controller.Pedido;

import com.inventory.manager.controller.UsersController;
import com.inventory.manager.model.*;
import com.inventory.manager.repository.IClienteRepository;
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

    @Autowired
    private IClienteRepository clienteRepository;

    @GetMapping("/getPedidos")
    public List<Pedido> pedidoList() {
        logger.info("Return pedidoList");
        return this.pedidoService.findAllPedido();
    }

    @PostMapping("/savePedido")
    public ResponseEntity<Pedido> savePedido(@RequestBody PedidoRequest request) {
        try {
            logger.info("Llegue aqui!!!");

            Pedido pedido = request.getPedido(); //Obtener pedido
            List<Inventory> productos = request.getProductos(); //Obtener los productos
            List<Integer> cantidades = request.getCantidades(); //Obtener las cantidades
            Client client = clienteRepository.findById(request.getPedido().getClient().getId()).orElse(null);

            pedido.setClient(client);
            Pedido savedPedido = pedidoService.savePedido(pedido, productos, cantidades);
            return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);

            // Verificar si el cliente es un objeto completo o solo un ID
//            if (pedido.getClient() != null && pedido.getClient().getId() != null) {
//                Long clientId = pedido.getClient().getId();
//                // Buscar el cliente completo a partir del ID y asignarlo al pedido
//                Client client = clienteRepository.findById(clientId).orElse(null);
//                pedido.setClient(client);
//            }
        } catch (Exception e) {
            logger.info("Hola soy exception");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

}
