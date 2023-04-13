package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dao.ItemRepository;
import com.l2i_e_commerce.model.Item;

import jakarta.annotation.PostConstruct;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl<T extends Item, ID> implements ItemService<T, ID> {

    @Autowired
    private ItemRepository<T, ID> itemRepository;

    @PostConstruct
    public void postConstruct() {
        System.err.println("XXXXXXXXXXX ItemRepository: " + itemRepository);
    }
    
    @Override
    public List<T> findAll() throws Exception {
        return itemRepository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) throws Exception {
        return itemRepository.findById(id);
    }

    @Override
    public T update(T item) throws Exception {
        return itemRepository.save(item);
    }

	@Override
	public T save(T itemClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
	/*
	 * @Override public List<Item> findItemsInStock() throws Exception { // Vous
	 * devez définir les critères de recherche pour les articles en stock // Par
	 * exemple, en utilisant un attribut "stock" dans vos objets Item String
	 * searchQuery = "stock>0"; return meiliSearchService.search(searchQuery); }
	 * 
	 * @Override public List<Item> findMostSoldItems() throws Exception { // Vous
	 * devez définir les critères de recherche pour les articles les plus vendus //
	 * Par exemple, en utilisant un attribut "sold_count" dans vos objets Item
	 * String searchQuery = "sort=sold_count:desc"; return
	 * meiliSearchService.search(searchQuery); }
	 */




