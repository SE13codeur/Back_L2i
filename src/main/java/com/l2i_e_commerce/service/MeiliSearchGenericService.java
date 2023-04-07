package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Item;

import java.util.*;

public interface MeiliSearchGenericService<T extends Item> {
    T save(T item) throws Exception;
    T update(T item) throws Exception;
    Optional<T> findById(String id) throws Exception;
    void deleteById(String id) throws Exception;
	/*
	 * List<T> findItemsInStock() throws Exception; List<T> findMostSoldItems()
	 * throws Exception;
	 */
}



