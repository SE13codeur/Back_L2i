package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.*;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("items/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CartDTO cartDTO) {
        if (cartDTO.getCartItems().size() > 0 ) {

            Order order = new Order();

            User user = this.userService.findByUsername(cartDTO.getUser().getUsername());
            Optional<Address> billingAddress = this.addressService.findById(cartDTO.getBillingAddressId());
            if (billingAddress.isPresent()) {
                Address orderBillingAddress = billingAddress.get();
                order.setBillingAddress(orderBillingAddress);
            }

            Optional<Address> shippingAddress = this.addressService.findById(cartDTO.getShippingAddressId());
            if (shippingAddress.isPresent()) {
                Address orderShippingAddress = shippingAddress.get();
                order.setShippingAddress(orderShippingAddress);
            }

            // Generate Order Number
            String orderNumber = generateOrderNumber(user);
            order.setOrderNumber(orderNumber);

            // Set user, billing address, shipping address, etc.
            order.setUser(user);
            order.setStatus(OrderStatus.PENDING);
            order.setDate(new Date());

            // Initialize total prices
            order.setTotalPriceHT(0.0);
            order.setTotalPriceTTC(0.0);

            List<OrderLine> orderLines = new ArrayList<>();
            orderService.save(order);

            List<CartItemDTO> cartItems = cartDTO.getCartItems();
            if (cartItems != null) {
                for (CartItemDTO cartItemDTO : cartItems) {
                    OrderLine orderLine = new OrderLine();

                    Optional<Book> bookOptional = this.bookService.findById(cartItemDTO.getId());

                    // Check if the book is present
                    if (bookOptional.isPresent()) {
                        Book book = bookOptional.get();
                        orderLine.setBook(book);

                        if (cartItemDTO.getQuantity() > 0 && cartItemDTO.getRegularPrice() > 0) {
                            orderLine.setOrderedQuantity(cartItemDTO.getQuantity());
                            orderLine.setUnitPriceTTC(cartItemDTO.getRegularPrice());
                            orderLine.setTva(cartItemDTO.getTva());
                            orderLine.setUnitPriceHT(Math.round(orderLine.getUnitPriceTTC() * (1 - orderLine.getTva().getTvaRate()) * 100.0) / 100.0);
                            order.setTotalPriceHT(order.getTotalPriceHT() + orderLine.getOrderedQuantity() * orderLine.getUnitPriceHT());
                            order.setTotalPriceTTC(order.getTotalPriceTTC() + orderLine.getOrderedQuantity() * orderLine.getUnitPriceTTC());
                            orderLine.setOrder(order);
                            orderLines.add(orderLine);

                            orderLineService.save(orderLine);
                        } else {
                            // Handle case where quantity or price is zero.
                        }
                    } else {
                        // Handle case where book is not found.
                    }
                }
            }
            order.setTotalPriceHT(Math.round(order.getTotalPriceHT() * 100.0) / 100.0);
            order.setOrderLines(orderLines);

            // Save the order after you have created and added all the order lines
            Order createdOrder = orderService.update(order);

            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public List<Order> getOrdersByUserId(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return orderService.findByUserId(user.get().getId());
        }
        return new ArrayList<>();
    }

    private String generateOrderNumber(User user) {
        // Fetch the last order number for the user
        String lastOrderNumber = orderService.findLastOrderNumberByUser(user);

        // Extract sequence number and increment it
        int sequence = 0;
        if (lastOrderNumber != null) {
            sequence = Integer.parseInt(lastOrderNumber.split("-")[1]);
        }
        sequence++;

        // Construct the new order number
        return "S" + String.format("%03d", user.getId()) + "-" + String.format("%07d", sequence);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderService.findById(id);
        if (order != null) {
            order.setUser(orderDetails.getUser());
            order.setTotalPriceHT(orderDetails.getTotalPriceHT());
            order.setTotalPriceTTC(orderDetails.getTotalPriceTTC());
            order.setStatus(orderDetails.getStatus());
            order.setDate(orderDetails.getDate());
            *//*order.setBillingAddress(orderDetails.getBillingAddress());
            order.setShippingAddress(orderDetails.getShippingAddress());*//*
            order.setOrderLines(orderDetails.getOrderLines());
            return new ResponseEntity<>(orderService.update(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

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



