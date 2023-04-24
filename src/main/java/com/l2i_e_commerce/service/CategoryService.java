package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAll();
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
    boolean categoryExists(String name, Long parentId);
    Optional<Category> findByNameAndParentId(String name, Long parentId);

}
