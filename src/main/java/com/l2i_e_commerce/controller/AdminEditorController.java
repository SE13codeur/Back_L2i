package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.Editor;
import com.l2i_e_commerce.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/editors")
public class AdminEditorController {

    @Autowired
    private EditorService editorService;

    @PostMapping
    public ResponseEntity<Editor> createEditor(@RequestBody Editor editor) {
        return ResponseEntity.ok(editorService.save(editor));
    }

    @GetMapping
    public ResponseEntity<List<Editor>> getAllEditors() {
        return ResponseEntity.ok(editorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editor> getEditorById(@PathVariable Long id) {
        return ResponseEntity.ok(editorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editor> updateEditor(@PathVariable Long id, @RequestBody Editor updatedEditor) {
        return ResponseEntity.ok(editorService.update(id, updatedEditor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable Long id) {
        editorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
