package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;

public interface BookService extends ItemService{
	public Book save(Book item) throws Exception;
    Book update(Book book);
    void deleteById(Long id);
}

