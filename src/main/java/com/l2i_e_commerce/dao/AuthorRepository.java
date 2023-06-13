package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Author;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	List<Author> findByFirstnameAndLastname(String firstname, String lastname);
    Set<Author> findByBooks_Id(Long bookId);
}




