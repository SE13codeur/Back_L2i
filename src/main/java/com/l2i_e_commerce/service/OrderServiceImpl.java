package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.dao.OrderRepository;

import com.l2i_e_commerce.model.OrderStatus;
import com.l2i_e_commerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public boolean delete(Order order) {
        orderRepository.delete(order);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByUserId(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public String findLastOrderNumberByUser(User user) {
        List<String> orderNumbers = orderRepository.findOrderNumbersByUser(user);
        return orderNumbers.isEmpty() ? null : orderNumbers.get(0);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order updatedOrder = order.get();
            updatedOrder.setStatus(status);
            orderRepository.save(updatedOrder);
        }
    }
}
