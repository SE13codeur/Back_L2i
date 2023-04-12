package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.Item;
import com.l2i_e_commerce.service.MeiliSearchGenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/items")
public class GenericItemController<T extends Item> {

    private final MeiliSearchGenericService<T> itemService;

    protected GenericItemController(MeiliSearchGenericService<T> service) {
        this.itemService = service;
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


    @PostMapping
    public ResponseEntity<T> save(@RequestBody T item) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable String id, @RequestBody T item) throws Exception {
        if (!itemService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        item.setMeiliSearchId(id);
        return ResponseEntity.ok(itemService.update(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws Exception {
        if (!itemService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        itemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
 }   
	/*
	 * @GetMapping("/in-stock") public ResponseEntity<List<T>> findItemsInStock() {
	 * return ResponseEntity.ok(itemService.findItemsInStock()); }
	 * 
	 * @GetMapping("/most-sold") public ResponseEntity<List<T>> findMostSoldItems()
	 * { return ResponseEntity.ok(itemService.findMostSoldItems()); }
	 */



