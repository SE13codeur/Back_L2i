package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Editor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorRepository extends JpaRepository<Editor, Long> {
    Editor findByName(String name);
    Editor findByBooks_Id(Long bookId);
}


