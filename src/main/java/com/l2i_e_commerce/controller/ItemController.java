package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
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
    
    @GetMapping("/in-stock")
    public ResponseEntity<List<Item>> findItemsInStock() {
        return ResponseEntity.ok(itemService.findItemsInStock());
    }

    @GetMapping("/most-sold")
    public ResponseEntity<List<Item>> findMostSoldItems() {
        return ResponseEntity.ok(itemService.findMostSoldItems());
    }
}

