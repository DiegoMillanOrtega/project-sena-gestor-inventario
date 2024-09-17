package com.inventory.manager.repository;

import com.inventory.manager.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductContaining(String product);

    @Query("SELECT i FROM Inventory i INNER JOIN i.category c WHERE c.category = :category")
    List<Inventory> findByProductsByCategory(@Param("category") String category);
    //List<Inventory> findByCategoryContaining(String category);

}
