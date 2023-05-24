package com.l2i_e_commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.l2i_e_commerce.model.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "editors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "editor")
    private Set<Book> books;

    public Editor(String name) {
        this.name = name;
    }
}