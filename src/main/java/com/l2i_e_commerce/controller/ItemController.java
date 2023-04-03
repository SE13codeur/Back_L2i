package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.service.ItemService;


public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

}

