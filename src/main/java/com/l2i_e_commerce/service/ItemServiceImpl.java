package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Item;
import com.l2i_e_commerce.dao.ItemRepository;
import com.l2i_e_commerce.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
    
    @Override
    public List<Item> searchByTitleOrAuthor(String query) {
        return itemRepository.searchByTitleOrAuthor(query);
    }
    
    @Override
    public List<Item> findItemsInStock() {
        return itemRepository.findItemsInStock();
    }

    @Override
    public List<Item> findMostSoldItems() {
        return itemRepository.findMostSoldItems();
    }

    
}

