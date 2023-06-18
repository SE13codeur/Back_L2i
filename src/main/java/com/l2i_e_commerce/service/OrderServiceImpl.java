package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.dao.OrderRepository;

import com.l2i_e_commerce.model.OrderStatus;
import com.l2i_e_commerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public String findLastOrderNumberByUser(User user) {
        List<String> orderNumbers = orderRepository.findOrderNumbersByUser(user);
        return orderNumbers.isEmpty() ? null : orderNumbers.get(0);
    }

    public void updateOrderStatus(String username, String orderNumber, OrderStatus status) throws Exception {
        User user = userService.findByUsername(username);
        List<Order> orders = orderRepository.findByUser(user);

        if (orders != null && !orders.isEmpty()) {
            Order targetOrder = null;
            for (Order order : orders) {
                if (order.getOrderNumber().equals(orderNumber)) {
                    targetOrder = order;
                    break;
                }
            }

            if (targetOrder != null) {
                targetOrder.setStatus(status);
                orderRepository.save(targetOrder);
            } else {
                throw new Exception("Order not found for order number: " + orderNumber);
            }
        } else {
            throw new Exception("Orders not found for username: " + username);
        }
    }


}
