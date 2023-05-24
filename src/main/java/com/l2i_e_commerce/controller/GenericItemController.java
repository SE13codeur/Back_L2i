package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/items")
public abstract class GenericItemController<T extends Item> {

    protected final ItemService itemService;

    @Autowired
    protected GenericItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    protected ResponseEntity<T> createItem(@RequestBody T item) {
        try {
            T createdItem = (T) itemService.save(item);
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable String id, @RequestBody T item) throws Exception {
        if (!itemService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        item.setMeiliSearchId(id);
        return ResponseEntity.ok(itemService.update(item));
    }
}
