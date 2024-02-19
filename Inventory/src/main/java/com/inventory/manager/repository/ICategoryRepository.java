package com.inventory.manager.repository;

import com.inventory.manager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryContaining(String category);
}
