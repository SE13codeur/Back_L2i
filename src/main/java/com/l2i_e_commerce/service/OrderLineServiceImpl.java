package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.OrderLine;
import com.l2i_e_commerce.dao.OrderLineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineServiceImpl(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    public OrderLine save(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Override
    public void delete(OrderLine orderLine) {
        orderLineRepository.delete(orderLine);
    }

    @Override
    public void deleteById(Long id) {
        orderLineRepository.deleteById(id);
    }

    @Override
    public OrderLine findById(Long id) {
        return orderLineRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderLine> findAll() {
        return orderLineRepository.findAll();
    }
}
