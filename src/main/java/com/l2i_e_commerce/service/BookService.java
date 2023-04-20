package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dao.*;
import com.l2i_e_commerce.model.Book;

import jakarta.transaction.Transactional;

public interface BookService  extends BookRepository, ItemService<Book, Long>{

}

