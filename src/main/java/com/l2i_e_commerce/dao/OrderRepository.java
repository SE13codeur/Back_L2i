package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
