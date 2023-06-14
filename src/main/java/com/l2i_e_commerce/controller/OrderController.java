package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.*;
import com.l2i_e_commerce.model.Order;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("items/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineService orderLineService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if(orderService.save(order) != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CartDTO cartDTO, @RequestBody CartItemDTO[] cartItemsDTO) {
        Order order = new Order();

        // Set user, billing address, shipping address, etc.
        order.setUser(cartDTO.getUser());
        order.setBillingAddress(cartDTO.getBillingAddress());
        order.setShippingAddress(cartDTO.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setDate(new Date());

        // Populate OrderLines from CartItemDTOs
        order.setTotalPriceHT(0.0);
        order.setTotalPriceTTC(0.0);
        orderService.save(order);

        List<OrderLine> orderLines = new ArrayList<>();

        List<CartItemDTO> cartItems = cartDTO.getCartItems();
        if (cartItems != null) {
            for (CartItemDTO cartItemDTO : cartItemsDTO) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderedQuantity(cartItemDTO.getQuantity());
                orderLine.setUnitPriceTTC(cartItemDTO.getRegularPrice());
                orderLine.setTva(cartItemDTO.getTva());
                orderLine.setUnitPriceHT(orderLine.getUnitPriceTTC() * (1 - orderLine.getTva().getTvaRate()));
                order.setTotalPriceHT(order.getTotalPriceHT() + orderLine.getOrderedQuantity() * orderLine.getUnitPriceHT());
                order.setTotalPriceTTC(order.getTotalPriceTTC() + orderLine.getOrderedQuantity() * orderLine.getUnitPriceTTC());
                orderLine.setOrder(order);
                orderLines.add(orderLine);

                orderLineService.save(orderLine);
            }
        }

        order.setOrderLines(orderLines);

        Order createdOrder = orderService.update(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderService.findById(id);

        if (order != null) {

            order.setUser(orderDetails.getUser());
            order.setTotalPriceHT(orderDetails.getTotalPriceHT());
            order.setTotalPriceTTC(orderDetails.getTotalPriceTTC());
            order.setStatus(orderDetails.getStatus());
            order.setDate(orderDetails.getDate());
            order.setBillingAddress(orderDetails.getBillingAddress());
            order.setShippingAddress(orderDetails.getShippingAddress());
            order.setOrderLines(orderDetails.getOrderLines());

            return new ResponseEntity<>(orderService.update(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order != null) {
            orderService.deleteById(id);
            return ResponseEntity.ok("Order deleted successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to delete order.");
    }
}
