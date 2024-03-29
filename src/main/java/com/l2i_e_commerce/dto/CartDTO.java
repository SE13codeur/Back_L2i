package com.l2i_e_commerce.dto;

import com.l2i_e_commerce.model.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private List<CartItemDTO> cartItems;
    private User user;
}