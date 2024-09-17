package com.inventory.manager.controller.Inventory;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.repository.IInventoryRepository;
import com.inventory.manager.service.Inventory.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(value = "http://localhost:4200")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private IInventoryRepository iInventoryRepository;
    public final static Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/getInventory")
    public List<Inventory> inventoryList() {
        return this.inventoryService.inventoryList();
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Optional<Inventory>> getProductById(@PathVariable Long id) {
        try {
            Optional<Inventory> product = this.inventoryService.getProductById(id);

            if (product.isPresent()) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public List<Inventory> searchProductByName(@RequestParam String product) {
        return inventoryService.buscarProductosPorNombre(product);
    }

    @GetMapping("/searchCategory/{category}")
    public ResponseEntity<List<Inventory>> searchProductByCategory(@PathVariable String category) {
       List<Inventory> products = this.inventoryService.findByProductsByCategory(category);
       return ResponseEntity.ok(products);
    }

    @PostMapping("/postProduct")
    public ResponseEntity<String> saveProduct(@RequestBody Inventory product) {
        try {
            this.inventoryService.saveProduct(product);
            return ResponseEntity.ok("Product saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product");
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            this.inventoryService.deleteProductId(id);
            return ResponseEntity.ok("Product eliminated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
        }
    }

}
