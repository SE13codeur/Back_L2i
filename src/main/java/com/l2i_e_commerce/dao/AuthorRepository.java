package com.l2i_e_commerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.l2i_e_commerce.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameContainingIgnoreCase(String name);
}

