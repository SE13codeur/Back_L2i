package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantityInStock;

    public Stock(Item item, int quantityInStock) {
        this.item = item;
        this.quantityInStock = quantityInStock;
    }
}

