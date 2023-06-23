package com.l2i_e_commerce.dto;

import com.l2i_e_commerce.model.OrderStatus;

public class OrderStatusUpdateDTO {

    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
