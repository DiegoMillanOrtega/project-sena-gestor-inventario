package com.inventory.manager.service.Inventory;

import com.inventory.manager.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    public List<Inventory> inventoryList();
    public void saveProduct(Inventory product);
    public void deleteProductId(Long id);
    public Optional<Inventory> getProductById(Long id);

    List<Inventory> buscarProductosPorNombre(String nombre);
}
