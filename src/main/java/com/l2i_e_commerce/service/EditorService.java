package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Editor;

import java.util.List;

public interface EditorService {
    Editor save(Editor editor);
    List<Editor> findAll();
    Editor findById(Long id);
    Editor update(Long id, Editor updatedEditor);
    void delete(Long id);
}
