package com.l2i_e_commerce.dao;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.model.TVA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVARepository extends JpaRepository<Book, Long> {
    static void save(TVA tva20) {
    }
}