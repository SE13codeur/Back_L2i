package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {

	List<Item> findAll();
    
    Optional<Item> findById(Long id);
    
    Item save(Item item);
    
    Item update(Item item);
    
    void deleteById(Long id);List<Item> findItemsInStock();
        
    List<Item> findMostSoldItems();
}

