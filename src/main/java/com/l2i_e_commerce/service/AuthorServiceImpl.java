package com.l2i_e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.l2i_e_commerce.dao.AuthorRepository;
import com.l2i_e_commerce.model.Author;

@SuppressWarnings("rawtypes")
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }
}
