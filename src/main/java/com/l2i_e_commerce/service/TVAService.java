package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.model.TVA;

import java.util.List;

public interface TVAService {
    TVA save(TVA tva);
    TVA update(TVA tva);
    void delete(TVA tva);
    void deleteById(Long id);
    TVA findById(Long id);
}
