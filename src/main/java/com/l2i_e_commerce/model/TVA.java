package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class TVA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String tvaType;

    Double tvaRate;

    @OneToMany(mappedBy = "tva")
    List<Book> books;

    public TVA() {

    }

    public TVA(String s, String tva20, double tvaRate, ArrayList<Book> books) {
    }
}