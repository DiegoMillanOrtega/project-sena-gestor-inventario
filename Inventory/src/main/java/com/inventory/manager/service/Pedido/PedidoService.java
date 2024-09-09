package com.inventory.manager.service.Pedido;

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
    public Pedido savePedido(Pedido pedido, List<Inventory> productos, List<Integer> cantidades) {
        // Crear una lista para los detalles del pedido
        List<PedidoDetalle> detalles = new ArrayList<>();

        // Verificar que el tamaño de productos y cantidades coincida
        if (productos.size() != cantidades.size()) {
            throw new IllegalArgumentException("La cantidad de productos y cantidades no coinciden");
        }

        // Asignar los productos y cantidades al pedido
        for (int i = 0; i < productos.size(); i++) {
            Inventory producto = productos.get(i);
            Integer cantidad = cantidades.get(i);

            // Crear un nuevo detalle de pedido
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidades(cantidad);

            detalles.add(detalle);
        }
        // Establecer los detalles del pedido al objeto pedido
        pedido.setPedidoDetalles(detalles);

        // Si el cliente no está en la base de datos, lanzamos una excepción
        if (pedido.getClient() == null || !clienteRepository.existsById(pedido.getClient().getId())) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        return pedidoRepository.save(pedido);
    }
}
