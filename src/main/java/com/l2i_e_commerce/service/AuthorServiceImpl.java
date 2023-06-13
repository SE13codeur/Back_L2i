package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Author;
import com.l2i_e_commerce.dao.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findByFirstnameAndLastname(String firstname, String lastname) {
        return authorRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public Author update(Long id, Author updatedAuthor) {
        findById(id);
        updatedAuthor.setId(id);
        return authorRepository.save(updatedAuthor);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        authorRepository.deleteById(id);
    }
}
