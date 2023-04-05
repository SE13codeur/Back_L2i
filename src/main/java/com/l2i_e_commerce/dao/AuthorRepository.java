package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Author;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	List<Author> findByFirstNameAndLastName(String firstName, String lastName);
}



