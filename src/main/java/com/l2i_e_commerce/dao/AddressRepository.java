
package com.l2i_e_commerce.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.l2i_e_commerce.model.Address;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(Optional<Address> id);

    List<Address> findByUserId(Long userId);
}