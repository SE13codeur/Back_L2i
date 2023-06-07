package com.l2i_e_commerce.dto;

import lombok.Data;

@Data
public class UserDto {
    String username;
    String password;
    String grantType;
    String refreshToken;

}
