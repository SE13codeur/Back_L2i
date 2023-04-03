package com.l2i_e_commerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.model.Author;
import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.dao.AuthorRepository;
import com.l2i_e_commerce.dao.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
    private BookRepository bookRepository;
    
	@Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchBooksByAuthorName(String authorName) {
            List<Author> authors = authorRepository.findByNameContainingIgnoreCase(authorName);
            return authors.stream()
                    .flatMap(author -> author.getBooks().stream())
                    .distinct()
                    .collect(Collectors.toList());
    }
    
}


