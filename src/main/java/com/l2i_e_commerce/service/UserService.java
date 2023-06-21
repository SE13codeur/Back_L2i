package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> findAll();
    User save(User user);
    User updateUser(Long id, User updatedUser);
    }
