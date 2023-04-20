package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ItemRepository<Book, Long> {	
}