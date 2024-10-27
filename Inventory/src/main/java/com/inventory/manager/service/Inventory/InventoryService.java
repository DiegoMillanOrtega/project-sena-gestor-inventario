package com.inventory.manager.service.Inventory;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.InventoryDTO;
import com.inventory.manager.repository.IInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private IInventoryRepository iInventoryRepository;

    @Override
    public List<InventoryDTO> inventoryList() {
        List<Inventory> inventories = this.iInventoryRepository.findAll();
        return inventories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO convertToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getId(),
                inventory.getProduct(),
                inventory.getCategory(),
                inventory.getPrice(),
                inventory.getStock()
        );
    }

    @Override
    public void saveProduct(Inventory product) {
        this.iInventoryRepository.save(product);
    }

    @Override
    public void deleteProductId(Long id) {
        this.iInventoryRepository.deleteById(id);
    }

    @Override
    public Optional<Inventory> getProductById(Long id) {
        Optional<Inventory> productOptional = this.iInventoryRepository.findById(id);
        if (productOptional.isPresent()) {
            Inventory product = productOptional.get();
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Inventory> buscarProductosPorNombre(String product) {
        return iInventoryRepository.findByProductContaining(product);
    }

    @Override
    public List<Inventory> findByProductsByCategory(String category) {
        if (category.isEmpty()) {
            return null;
        }
        try {
            return iInventoryRepository.findByCategoryCategory(category);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se Encontró un Producto con esa Categoria.");
        }
    }

    @Override
    public List<Inventory> findOutOfStockProducts() {
        return iInventoryRepository.findByStockLessThan(5);
    }

    @Override
    public List<Inventory> findByProductsByCategoryOutOfStock(String category) {
        if (category.isEmpty()) {
            return null;
        }
        try {
            return iInventoryRepository.findByCategoryCategoryAndStockLessThan(category, 5);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontró Productos sin stock con esa Categoria.");
        }
    }

    @Override
    public List<Inventory> buscarProductosPorNombreSinStock(String product) {
        if (product.isEmpty()) {
            return null;
        }
        try {
            List<Inventory> inventories = iInventoryRepository.findByProductContainingAndStockLessThan(product,5);
            if (inventories.isEmpty()) {
                System.out.println("Vacio");
            }
            return iInventoryRepository.findByProductContainingAndStockLessThan(product,5);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al buscar Productos por el nombre sin stock: " + e.getMessage(), e);
        }
    }
}
