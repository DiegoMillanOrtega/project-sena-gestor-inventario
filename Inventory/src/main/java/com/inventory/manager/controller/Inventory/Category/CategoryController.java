package com.inventory.manager.controller.Inventory.Category;

import com.inventory.manager.model.Category;
import com.inventory.manager.service.Inventory.Category.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(value = "http://localhost:4200")
public class CategoryController {
    public final static Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public List<Category> categoryList() {
        logger.info("Return categoryList ok");
        return this.categoryService.findAll();
    }

    @GetMapping("/search")
    public List<Category> searchCategoryByName(@RequestParam String category) {
        return this.categoryService.findByCategory(category);
    }

    @GetMapping("/searchById/{id}")
    public Category searchCategoryById(@PathVariable Long id) {
        return this.categoryService.findByCategoryId(id);
    }

    @PostMapping("/saveCategory")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        try {
            Category savedCategory = this.categoryService.saveCategory(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            this.categoryService.deleteCategory(id);
            return ResponseEntity.ok("Deleted category successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting category");
        }
    }
}
