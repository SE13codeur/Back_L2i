package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    Order update(Order order);
    void delete(Order order);
    void deleteById(Long id);
    Order findById(Long id);
    List<Order> findAll();
}
