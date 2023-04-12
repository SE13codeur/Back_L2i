package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dao.ItemRepository;
import com.l2i_e_commerce.model.Item;
import com.meilisearch.sdk.Client;

import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private MeiliSearchGenericService<Item> meiliSearchService;
    
    @SuppressWarnings("rawtypes")
	@Autowired
    private ItemRepository itemRepository;
    public ItemServiceImpl() throws Exception {
		this.meiliSearchService = null;
    }
    
    public ItemServiceImpl(Client client) throws Exception {
        meiliSearchService = new MeiliSearchGenericServiceImpl<Item>(client, "items");
    }
    
    public ItemServiceImpl(Client client, String indexUid) throws Exception {
        meiliSearchService = new MeiliSearchGenericServiceImpl<Item>(client, indexUid);
    }

    @Override
    public void index(List<Item> items) throws Exception {
        meiliSearchService.index(items);
    }

    
    @Override
    public List<Item> findAll() throws Exception {
        return meiliSearchService.findAll();
    }

    @Override
    public Optional<Item> findById(String id) throws Exception {
        return meiliSearchService.findById(id);
    }

    @Override
    public Item save(Item item) throws Exception {
        return meiliSearchService.save(item);
    }

    @Override
    public Item update(Item item) throws Exception {
        return meiliSearchService.update(item);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void deleteById(String id) throws Exception {
        Long itemId = Long.parseLong(id);
        itemRepository.deleteById(itemId);
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




