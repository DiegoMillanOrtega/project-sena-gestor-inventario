package com.inventory.manager.service.Inventory;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.InventoryDTO;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    public List<InventoryDTO> inventoryList();
    public void saveProduct(Inventory product);
    public void deleteProductId(Long id);
    public Optional<Inventory> getProductById(Long id);

    List<Inventory> buscarProductosPorNombre(String nombre);

    List<Inventory> findByProductsByCategory (String category);
    List<Inventory> findOutOfStockProducts();
    List<Inventory> findByProductsByCategoryOutOfStock (String category);


    List<Inventory> buscarProductosPorNombreSinStock (String product);
}
