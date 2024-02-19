package com.inventory.manager.service.Inventory;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.repository.IInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService{

    @Autowired
    private IInventoryRepository iInventoryRepository;
    @Override
    public List<Inventory> inventoryList() {
        return this.iInventoryRepository.findAll();
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
}
