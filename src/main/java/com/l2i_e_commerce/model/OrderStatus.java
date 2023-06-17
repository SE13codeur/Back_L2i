package com.l2i_e_commerce.model;

public enum OrderStatus {
    PENDING("En attente de confirmation"),
    CONFIRMED("Confirmé"),
    SHIPPING("En cours de livraison"),
    DELIVERED("Livré");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
