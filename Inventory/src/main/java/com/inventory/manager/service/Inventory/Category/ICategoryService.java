package com.inventory.manager.service.Inventory.Category;

import com.inventory.manager.model.Category;


import java.util.List;

public interface ICategoryService{
    public List<Category> findAll();
    public List<Category> findByCategory(String category);
    public Category findByCategoryId(Long id);
    public Category saveCategory(Category category);
    public void deleteCategory(Long id);

}
