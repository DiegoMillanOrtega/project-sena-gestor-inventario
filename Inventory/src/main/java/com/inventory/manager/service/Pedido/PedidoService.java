package com.inventory.manager.service.Pedido;

import com.inventory.manager.controller.Pedido.PedidoController;
import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.repository.IPedidoRepository;
import com.inventory.manager.service.Inventory.InventoryService;
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
    private InventoryService inventoryService;

    @Override
    public List<Pedido> findAllPedido() {
        return this.pedidoRepository.findAll();
    }

    @Override
    public void savePedido(Pedido pedido) {
        List<Inventory> productByName = this.inventoryService.buscarProductosPorNombre(pedido.getProduct());
        if (!productByName.isEmpty()) { // Verificar si la lista no está vacía
            Inventory product = productByName.get(0); // Obtener el primer elemento de la lista
            Integer stockProduct = product.getStock();
            stockProduct -= pedido.getStock();
            product.setStock(stockProduct);
            this.inventoryService.saveProduct(product);
        } else {
            // Manejar el caso en el que no se encuentra ningún producto
            logger.error("No se encontró ningún producto con el nombre especificado.");
            // Puedes lanzar una excepción, registrar un error, o manejar de otra forma según sea apropiado.
        }
        this.pedidoRepository.save(pedido);
    }

    @Override
    public void confirmedDelivery(Pedido updatePedido) {
        Pedido pedido = this.pedidoRepository.findById(updatePedido.getId()).orElse(null);
        if (pedido != null) {
            pedido.setConfirmedDelivery(updatePedido.isConfirmedDelivery());
            this.pedidoRepository.save(pedido);
            logger.info("updating successfully");
        } else {
            logger.info("Error updating for pedido null");
        }
    }
}
