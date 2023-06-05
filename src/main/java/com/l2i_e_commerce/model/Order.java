package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;

    Double totalPriceHT;

    Double totalPriceTTC;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    @ManyToOne
    Address billingAddress;

    @ManyToOne
    Address shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderLine> orderLines;
}

enum OrderStatus {
    PENDING, COMPLETED, CANCELLED
}