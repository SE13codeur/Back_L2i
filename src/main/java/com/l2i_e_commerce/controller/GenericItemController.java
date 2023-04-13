package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/items")
public class GenericItemController<T extends Item> {

    protected final ItemService itemService;

    @Autowired
    public GenericItemController(ItemService itemService) {
        this.itemService = itemService;
    }

	@GetMapping
    public ResponseEntity<List<T>> getAllItems() {
        try {
            List<T> items = itemService.findAll();
            return new ResponseEntity<>(items, HttpStatus.OK);
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
	/*
	 * @GetMapping("/in-stock") public ResponseEntity<List<T>> findItemsInStock() {
	 * return ResponseEntity.ok(itemService.findItemsInStock()); }
	 * 
	 * @GetMapping("/most-sold") public ResponseEntity<List<T>> findMostSoldItems()
	 * { return ResponseEntity.ok(itemService.findMostSoldItems()); }
	 */



