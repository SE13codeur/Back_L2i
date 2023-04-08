package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Item;

import java.util.*;

public interface MeiliSearchGenericService<T extends Item> {
	List<T> findAll() throws Exception;
	Optional<T> findById(String id) throws Exception;
    T save(T item) throws Exception;
    T update(T item) throws Exception;
    void deleteById(String id) throws Exception;
    void index(List<Item> items) throws Exception;
	/*
	 * List<T> findItemsInStock() throws Exception; List<T> findMostSoldItems()
	 * throws Exception;
	 */
}
