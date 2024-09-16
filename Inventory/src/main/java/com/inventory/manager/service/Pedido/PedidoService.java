package com.inventory.manager.service.Pedido;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.controller.Pedido.PedidoController;
import com.inventory.manager.model.Client;
import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDetalle;
import com.inventory.manager.repository.*;
import com.inventory.manager.service.Inventory.InventoryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService implements IPedidoService{

    public static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private IPedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private IInventoryRepository inventoryRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Pedido> findAllPedido() {
        return this.pedidoRepository.findAll();
    }

    @Override
    public Pedido savePedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("La entidad Pedido no puede ser nula.");
        }
        try {
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new DataBaseException("Error al guarda el pedido", e);
        }
    }
}
