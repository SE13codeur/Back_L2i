package com.l2i_e_commerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    private Set<Book> books;
    
    @Column(nullable = false)
    private BigDecimal discountAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer quantityThreshold;

    private Integer freeItems;
}
