package com.l2i_e_commerce.service;

import java.util.List;

public interface AuthorService<Author> {
    List<Author> searchAuthorsByName(String firstName, String lastName);
}
