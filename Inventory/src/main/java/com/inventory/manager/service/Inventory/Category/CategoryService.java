package com.inventory.manager.service.Inventory.Category;

import com.inventory.manager.model.Category;
import com.inventory.manager.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> findByCategory(String category) {
        return this.categoryRepository.findByCategoryContaining(category);
    }

    @Override
    public Category findByCategoryId(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
