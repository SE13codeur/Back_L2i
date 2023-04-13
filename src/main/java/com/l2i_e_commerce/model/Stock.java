package com.l2i_e_commerce.model;

import java.util.Set;

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

    @OneToOne
    private Item item;

    private int quantityInStock;

    public Stock(Item item, int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}

