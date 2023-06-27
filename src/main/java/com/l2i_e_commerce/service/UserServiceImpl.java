package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.*;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.dao.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        if(user.getAddresses() != null) {
            for(Address address : user.getAddresses()) {
                address.setUser(user);
            }
        }
        return userRepository.save(user);
    }



    @Override
    public User updateUser(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(Long id) {userRepository.deleteById(id);}
}

