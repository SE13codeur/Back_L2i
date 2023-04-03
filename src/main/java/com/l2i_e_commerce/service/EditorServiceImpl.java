package com.l2i_e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.l2i_e_commerce.dao.EditorRepository;
import com.l2i_e_commerce.model.Editor;

@Service
public class EditorServiceImpl implements EditorService {
    private final EditorRepository editorRepository;

    public EditorServiceImpl(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    @Override
    public Editor findById(Long id) {
        return editorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Editor> findAll() {
        return editorRepository.findAll();
    }

    @Override
    public Editor save(Editor editor) {
        return editorRepository.save(editor);
    }

    @Override
    public void deleteById(Long id) {
        editorRepository.deleteById(id);
    }
}
