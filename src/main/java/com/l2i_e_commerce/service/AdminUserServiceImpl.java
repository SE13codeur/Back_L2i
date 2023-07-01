package com.l2i_e_commerce.service;

import com.l2i_e_commerce.dao.UserRepository;
import com.l2i_e_commerce.model.Address;
import com.l2i_e_commerce.model.Role;
import com.l2i_e_commerce.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
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
    public void deleteById(Long id) {

    }
}

