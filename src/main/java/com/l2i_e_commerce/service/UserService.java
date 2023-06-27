package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    User updateUser(User updatedUser);

    void deleteById(Long id);
}
