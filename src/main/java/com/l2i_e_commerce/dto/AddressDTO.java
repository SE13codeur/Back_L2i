package com.l2i_e_commerce.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    private String title;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Long userId;

}
