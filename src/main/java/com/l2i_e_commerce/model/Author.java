package com.l2i_e_commerce.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;
    
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private Set<Book> books;
    
    public Author(String firstName, String lastName) {
        this.firstname = firstName;
        this.lastname = lastName;

    }
}


