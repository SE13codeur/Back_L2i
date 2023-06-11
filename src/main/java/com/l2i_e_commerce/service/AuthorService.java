package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);
    List<Author> findByFirstnameAndLastname(String firstname, String lastname);
    List<Author> findAll();
    Author findById(Long id);
    Author update(Long id, Author updatedAuthor);
    void delete(Long id);
}
