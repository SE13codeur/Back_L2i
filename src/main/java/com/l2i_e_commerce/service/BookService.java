package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService extends ItemService<Book, Long>{
    Page<Book> findByPage(int page, int size);
    List<Book> findAll();

    Optional<Book> findById(Long id);

    void deleteById(Long id);

    Book save(Book book);

}

