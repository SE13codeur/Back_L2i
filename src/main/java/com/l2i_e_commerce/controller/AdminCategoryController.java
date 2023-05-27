package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;

import com.l2i_e_commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
    
    @GetMapping("/books")
    public ResponseEntity<Map<String, List<Book>>> getBooksByCategory() {
        List<Category> categories = categoryService.findAll();
        Map<String, List<Book>> booksByCategory = new HashMap<>();

        for (Category category : categories) {
            booksByCategory.put(category.getName(), category.getBooks());
        }

        return ResponseEntity.ok(booksByCategory);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

}

