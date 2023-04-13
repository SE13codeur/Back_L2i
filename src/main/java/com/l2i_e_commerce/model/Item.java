package com.l2i_e_commerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class Item implements MeiliSearchModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageUrl;
    
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private BigDecimal regularPrice;
    
    private int quantityInStock;
    
    private boolean isInStock;
    
    private boolean isNewCollection;
    
    private String language;
    
    private int totalSales;

    @JsonIgnore
    private LocalDateTime createdAt;
    
    @JsonIgnore
    private LocalDateTime updatedAt;
    
    @Override
    public String getMeiliSearchId() {
        return String.valueOf(getId());
    }
    
    @Override
    public void setMeiliSearchId(String meiliSearchId) {
        this.id = Long.parseLong(meiliSearchId);
    }
    
    public Item(String imageUrl, String description, BigDecimal regularPrice, boolean isInStock, boolean isNewCollection, String language, int totalSales) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.regularPrice = regularPrice;
        this.isInStock = isInStock;    	
        this.isNewCollection = isNewCollection;
        this.language = language;
        this.totalSales = totalSales;

    }
}

