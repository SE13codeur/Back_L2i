package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dto.OrderLineDto;
import com.l2i_e_commerce.model.OrderLine;
import com.l2i_e_commerce.dao.OrderLineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<OrderLineDto> getOrderLinesByOrderId(Long orderId) {
        return orderLineRepository.findByOrderId(orderId).stream()
                .map(record -> new OrderLineDto(
                        (String) record[0],
                        (String) record[1],
                        (Double) record[2],
                        (Double) record[3],
                        (Double) record[4],
                        (Integer) record[5]))
                .collect(Collectors.toList());
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Override
    public boolean delete(OrderLine orderLine) {
        orderLineRepository.delete(orderLine);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        orderLineRepository.deleteById(id);
        return true;
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
