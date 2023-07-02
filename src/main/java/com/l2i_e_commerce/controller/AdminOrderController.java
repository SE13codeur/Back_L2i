package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.*;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<Order> findAll() {
        List<User> users = this.userService.findAll();
        List<Order> orders = new ArrayList<>();
        for(User user : users) {
            List<Order> currentUserOrders = this.orderService.findByUserId(user.getId());
            for(Order currentOrder : currentUserOrders) {
               currentOrder.setUser(user);
               orders.add(currentOrder);
            }
        }
        return orders;
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateOrderStatusById(
            @PathVariable String id,
            @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) {
        System.err.println("id:" + id + "status:" + orderStatusUpdateDTO.getStatus());
        try {
            orderService.updateOrderStatus(Long.valueOf(id), orderStatusUpdateDTO.getStatus());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
