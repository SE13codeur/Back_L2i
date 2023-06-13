package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository<T extends Item, ID> extends JpaRepository<T, ID> {
}