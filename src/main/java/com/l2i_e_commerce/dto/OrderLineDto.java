package com.l2i_e_commerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class OrderLineDto {
    private String bookImageUrl;
    private String bookTitle;
    private Double unitPriceHT;
    private Double tvaRate;
    private Double unitPriceTTC;
    private Integer orderedQuantity;

    public OrderLineDto(String bookImageUrl, String bookTitle, Double unitPriceHT, Double tvaRate, Double unitPriceTTC, Integer orderedQuantity) {
        this.bookImageUrl = bookImageUrl;
        this.bookTitle = bookTitle;
        this.unitPriceHT = unitPriceHT;
        this.tvaRate = tvaRate;
        this.unitPriceTTC = unitPriceTTC;
        this.orderedQuantity = orderedQuantity;
    }
}
