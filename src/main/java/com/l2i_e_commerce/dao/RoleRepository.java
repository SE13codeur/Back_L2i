package com.l2i_e_commerce.dao;


import com.l2i_e_commerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role, Long> {
}