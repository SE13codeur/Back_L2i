package com.l2i_e_commerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.dao.*;

import java.util.Set;
import java.util.HashSet;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
	private EditorRepository editorRepository;

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
        Editor editor = book.getEditor();
        if (editor != null) {
            Editor existingEditor = editorRepository.findByName(editor.getName());
            if (existingEditor != null) {
                book.setEditor(existingEditor);
            } else {
                editorRepository.save(editor);
            }
        }

        Set<Author> authors = book.getAuthors();
        if (authors != null) {
            Set<Author> updatedAuthors = new HashSet<>();
            for (Author author : authors) {
                List<Author> existingAuthors = authorRepository.findByFirstNameAndLastName(
                        author.getFirstName(), author.getLastName());
                if (!existingAuthors.isEmpty()) {
                    updatedAuthors.addAll(existingAuthors);
                } else {
                    authorRepository.save(author);
                    updatedAuthors.add(author);
                }
            }
            book.setAuthors(updatedAuthors);
        }

        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override()
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchBooksByAuthorName(String firstName, String lastName) {
            List<Author> authors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
            return authors.stream()
                    .flatMap(author -> author.getBooks().stream())
                    .distinct()
                    .collect(Collectors.toList());
    }
    
}


