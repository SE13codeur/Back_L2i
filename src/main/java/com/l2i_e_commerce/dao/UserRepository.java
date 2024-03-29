package com.l2i_e_commerce.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.l2i_e_commerce.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);

}