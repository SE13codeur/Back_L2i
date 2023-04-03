package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.Item;
import com.l2i_e_commerce.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
        if (!itemService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        item.setId(id);
        return ResponseEntity.ok(itemService.update(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!itemService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        itemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchByTitleOrAuthor(@RequestParam String query) {
        return ResponseEntity.ok(itemService.searchByTitleOrAuthor(query));
    }
    
    @GetMapping("/in-stock")
    public ResponseEntity<List<Item>> findItemsInStock() {
        return ResponseEntity.ok(itemService.findItemsInStock());
    }

    @GetMapping("/most-sold")
    public ResponseEntity<List<Item>> findMostSoldItems() {
        return ResponseEntity.ok(itemService.findMostSoldItems());
    }
}

