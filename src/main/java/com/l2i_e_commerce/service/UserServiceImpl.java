package com.l2i_e_commerce.service;

import org.springframework.stereotype.Service;

import com.l2i_e_commerce.dao.UserRepository;
import com.l2i_e_commerce.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

