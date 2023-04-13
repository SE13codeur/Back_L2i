package com.l2i_e_commerce.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.l2i_e_commerce.model.Book;

public interface BookService  extends JpaRepository<Book, Long>, ItemService<Book, Long>{

}

