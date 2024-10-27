package com.inventory.manager.repository;

import com.inventory.manager.model.Inventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {


    List<Inventory> findByProductContaining(String product);

    // Encuentra productos por categoría
    List<Inventory> findByCategoryCategory(String category);

    // Encuentra productos con stock bajo
    List<Inventory> findByStockLessThan(int stock);

    // Encuentra productos por categoría y con stock bajo
    List<Inventory> findByCategoryCategoryAndStockLessThan(String category, int stock);


    List<Inventory> findByProductContainingAndStockLessThan(String product, int stock);

}
