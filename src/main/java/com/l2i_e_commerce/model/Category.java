package com.l2i_e_commerce.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    
    @OneToMany
    private List<Book> books;

    public Category(String name, String description, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
