package com.l2i_e_commerce.dto;

import com.l2i_e_commerce.model.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private String isbn13;
    private String imageUrl;
    private String title;
    private String subtitle;
    private String description;
    private Double regularPrice;
    private Double rating;
    private Integer quantityInStock;
    private Integer totalSales;
    private List<Author> authors;
    private Editor editor;
    private Category category;
    private String pages;
    private String year;
    private String language;
    private Integer version;
    private TVA tva;
    private Integer quantity;

}

