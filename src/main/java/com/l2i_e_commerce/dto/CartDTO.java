package com.l2i_e_commerce.dto;

import com.l2i_e_commerce.model.*;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private List<CartItemDTO> cartItems;
    private User user;
    private Long billingAddressId;
    private Long shippingAddressId;
}