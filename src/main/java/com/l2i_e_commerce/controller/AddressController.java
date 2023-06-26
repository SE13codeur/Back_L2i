package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.AddressDTO;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account/user/profile/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody AddressDTO addressDTO) {
        Address newAddress = new Address();
        Optional<User> user = userService.findById(addressDTO.getUserId());
        if (user.isPresent()) {
        newAddress.setUser(user.get());
        } else {
            newAddress.setUser(null);
        }

        newAddress.setStreet(addressDTO.getStreet());
        newAddress.setCity(addressDTO.getCity());
        newAddress.setZipCode(addressDTO.getZipCode());
        newAddress.setState(addressDTO.getState());
        newAddress.setCountry(addressDTO.getCountry());
        newAddress.setTitle(addressDTO.getTitle().isEmpty() ? addressDTO.getCity() : addressDTO.getTitle());

        addressService.save(newAddress);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        List<Address> addresses = addressService.findAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> findAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressService.findAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

