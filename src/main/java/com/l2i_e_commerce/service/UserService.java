package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.User;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
}
