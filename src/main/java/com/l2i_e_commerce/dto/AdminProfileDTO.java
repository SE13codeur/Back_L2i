package com.l2i_e_commerce.dto;

import lombok.Data;

@Data
public class AdminProfileDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String role;

}
