package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.model.OrderStatus;
import com.l2i_e_commerce.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Order save(Order order);
    Order update(Order order);

    boolean delete(Order order);

    boolean deleteById(Long id);

    Order findById(Long id);

    List<Order> findAll();

    List<Order> findByUserId(Long id);

    String findLastOrderNumberByUser(User user);

    void updateOrderStatus(Long id, OrderStatus status) throws Exception;
}