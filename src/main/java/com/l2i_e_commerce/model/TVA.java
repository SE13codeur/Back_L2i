/*
package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TVA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String tvaType;

    Double tvaRate;

    @OneToMany(mappedBy = "tva")
    List<Book> books;

}*/
