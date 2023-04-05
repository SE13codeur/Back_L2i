package com.l2i_e_commerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String imageUrl;
    
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private BigDecimal regularPrice;
    
    private boolean inStock;
    
    private boolean isNewCollection;
    
    private String language;
    
    @OneToOne(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Sale sale;
    
    private int totalSales;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    public Item(Book book, String imageUrl, String description, BigDecimal regularPrice, boolean inStock, boolean isNewCollection, String language, int totalSales) {
        this.book = book;
        this.imageUrl = imageUrl;
        this.description = description;
        this.regularPrice = regularPrice;
        this.inStock = inStock;    	
        this.isNewCollection = isNewCollection;
        this.language = language;
        this.totalSales = totalSales;

    }
}

