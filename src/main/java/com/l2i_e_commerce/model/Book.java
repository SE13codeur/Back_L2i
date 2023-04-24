package com.l2i_e_commerce.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "book")
public class Book extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn13;

    private String title;
    
    private String subtitle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private Set<Sale> bookSales;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category booksCategory;

    private String pages;
    private String year;
    private int version;
}
