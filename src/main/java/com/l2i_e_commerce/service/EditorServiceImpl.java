package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Editor;
import com.l2i_e_commerce.dao.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorRepository editorRepository;

    @Override
    public Editor save(Editor editor) {
        return editorRepository.save(editor);
    }

    @Override
    public List<Editor> findAll() {
        return editorRepository.findAll();
    }

    @Override
    public Editor findById(Long id) {
        Optional<Editor> editor = editorRepository.findById(id);
        return editor.orElseThrow(() -> new RuntimeException("Editor not found"));
    }

    @Override
    public Editor update(Long id, Editor updatedEditor) {
        findById(id);
        updatedEditor.setId(id);
        return editorRepository.save(updatedEditor);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        editorRepository.deleteById(id);
    }
}
