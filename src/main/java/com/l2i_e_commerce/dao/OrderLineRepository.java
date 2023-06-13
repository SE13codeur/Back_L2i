package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
