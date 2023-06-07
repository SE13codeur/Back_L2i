
package com.l2i_e_commerce.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.l2i_e_commerce.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

}