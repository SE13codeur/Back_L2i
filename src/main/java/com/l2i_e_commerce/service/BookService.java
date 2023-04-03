package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    Book update(Book book);
    void deleteById(Long id);
    
    List<Book> searchBooksByTitle(String title);
    
    List<Book> searchBooksByAuthorName(String authorName);
}

