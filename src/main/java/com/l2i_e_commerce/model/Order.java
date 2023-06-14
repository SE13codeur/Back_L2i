package com.l2i_e_commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
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

    @JsonBackReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderLine> orderLines;
}

