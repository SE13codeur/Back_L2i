package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Category;
import com.l2i_e_commerce.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean categoryExists(String name, Long parentId) {
        return categoryRepository.findByNameAndParentId(name, parentId).isPresent();
    }

    @Override
    public Optional<Category> findByNameAndParentId(String name, Long parentId) {
        return Optional.empty();
    }
}
