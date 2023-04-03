package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Category;
import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAll();
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
}
