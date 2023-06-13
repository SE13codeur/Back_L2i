package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemService<T extends Item, ID> {
    Optional<T> findById(ID id) throws Exception;
	T save(T itemClass);
    T update(T itemClass) throws Exception;
}

