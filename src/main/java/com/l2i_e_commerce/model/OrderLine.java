package com.l2i_e_commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }
}