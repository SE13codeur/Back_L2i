package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserId(Long id);

    @Query("SELECT o.orderNumber FROM Order o WHERE o.user = :user ORDER BY o.id DESC")
    List<String> findOrderNumbersByUser(@Param("user") User user);
}

