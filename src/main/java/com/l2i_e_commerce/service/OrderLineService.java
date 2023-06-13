package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.OrderLine;

import java.util.List;

public interface OrderLineService {
    OrderLine save(OrderLine orderLine);
    OrderLine update(OrderLine orderLine);
    void delete(OrderLine orderLine);
    void deleteById(Long id);
    OrderLine findById(Long id);
    List<OrderLine> findAll();
}
