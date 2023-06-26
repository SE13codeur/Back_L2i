package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address save(Address address);
    List<Address> findAll();
    Optional<Address> findById(Long id);
    List<Address> findAddressesByUserId(Long userId);
    Address updateAddress(Long id, Address address);
    void deleteById(Long id);



}

