package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.OrderLineDto;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items/orderlines")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    @GetMapping("/{id}")
    public List<OrderLineDto> getOrderlineByOrderId(@PathVariable Long id) {
        List<OrderLineDto> orderLinesDTO = orderLineService.getOrderLinesByOrderId(id);
        return orderLinesDTO;
    }


}
