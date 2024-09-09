package com.inventory.manager.repository;

import com.inventory.manager.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductContaining(String product);
    //List<Inventory> findByCategoryContaining(String category);

}
