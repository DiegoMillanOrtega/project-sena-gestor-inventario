package com.inventory.manager.repository;

import com.inventory.manager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

    //List<Category> findByCategoryContaining(String category);
}
