package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.dto.AdminProfileDTO;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<User> createUserFromAdmin(@RequestBody AdminProfileDTO adminProfileDTO) {
        System.err.println("entrée save user admin");
        User existingUser = userService.findByUsername(adminProfileDTO.getUsername());
        if (existingUser != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User user = new User();
        Role role = new Role();
        role.setTitle(RoleName.valueOf(adminProfileDTO.getRole()));
        roleService.save(role);
        user.setRole(role);
        user.setUsername(adminProfileDTO.getUsername());
        user.setEmail(adminProfileDTO.getEmail());
        user.setFirstname(adminProfileDTO.getFirstname());
        user.setLastname(adminProfileDTO.getLastname());
        user.setPassword(adminProfileDTO.getPassword());
        User newUser = adminUserService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }
}
