package com.l2i_e_commerce.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn13;

    @SuppressWarnings("unused")
	private String title;

    @SuppressWarnings("unused")
	private String subtitle;

    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @SuppressWarnings("unused")
	private String pages;

    @SuppressWarnings("unused")
	private String year;
    
    @SuppressWarnings("unused")
	private int version;

}


