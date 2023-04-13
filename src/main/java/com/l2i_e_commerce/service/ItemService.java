package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.*;

import java.util.List;
import java.util.Optional;

public interface ItemService<T extends Item, ID> {
    List<T> findAll() throws Exception;
    Optional<T> findById(ID id) throws Exception;
	T save(T itemClass);
    T update(T itemClass) throws Exception;
}


/*
 * void deleteById(Long id);List<Item> findItemsInStock();
 * 
 * List<Item> findMostSoldItems();
 */

