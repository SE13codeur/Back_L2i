package com.l2i_e_commerce.service;

import java.util.List;

import com.l2i_e_commerce.model.Editor;

public interface EditorService {
    Editor findById(Long id);
    List<Editor> findAll();
    Editor save(Editor editor);
    void deleteById(Long id);
}

