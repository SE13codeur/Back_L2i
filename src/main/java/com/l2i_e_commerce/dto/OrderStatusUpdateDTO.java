package com.l2i_e_commerce.dto;

import com.l2i_e_commerce.model.OrderStatus;

public class OrderStatusUpdateDTO {

    private String orderNumber;
    private OrderStatus status;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
