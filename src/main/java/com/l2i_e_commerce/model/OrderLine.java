package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer orderedQuantity;

    Double unitPriceHT;

    Double unitPriceTTC;

    @ManyToOne
    TVA tva;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    Book book;
}