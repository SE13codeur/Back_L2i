package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dto.OrderLineDto;
import com.l2i_e_commerce.model.OrderLine;

import java.util.List;

public interface OrderLineService {
    OrderLine save(OrderLine orderLine);
    List<OrderLineDto> getOrderLinesByOrderId(Long orderId);
    OrderLine update(OrderLine orderLine);
    boolean delete(OrderLine orderLine);
    boolean deleteById(Long id);
    OrderLine findById(Long id);
    List<OrderLine> findAll();
}
